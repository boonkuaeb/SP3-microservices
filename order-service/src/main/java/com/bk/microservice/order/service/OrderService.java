package com.bk.microservice.order.service;

import com.bk.microservice.order.client.InventoryClient;
import com.bk.microservice.order.dto.OrderRequest;
import com.bk.microservice.order.event.OrderPlacedEvent;
import com.bk.microservice.order.model.Order;
import com.bk.microservice.order.repositry.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;


    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (isProductInStock) {
            // Map OrderRequest to Order object
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            // Save to Order to OrderRepository
            orderRepository.save(order);

            // Send the message to Kafka Topic
            OrderPlacedEvent orderPlaceEvent = new OrderPlacedEvent(order.getOrderNumber(),orderRequest.userDetails().email());
            log.info("Start - Sending OrderPlaceEvent {} to Kafka topic order-placed",orderPlaceEvent);
            kafkaTemplate.send("order-placed",orderPlaceEvent);
            log.info("End - Sending OrderPlaceEvent {} to Kafka topic order-placed",orderPlaceEvent);

        } else {
            throw new RuntimeException("Product with Sku " + orderRequest.skuCode() + " is not in stock");
        }
    }
}
