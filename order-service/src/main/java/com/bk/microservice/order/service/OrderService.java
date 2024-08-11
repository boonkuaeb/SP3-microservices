package com.bk.microservice.order.service;

import com.bk.microservice.order.dto.OrderRequest;
import com.bk.microservice.order.model.Order;
import com.bk.microservice.order.repositry.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest)
    {
        // Map OrderRequest to Order object
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());

        // Save to Order to OrderRepository
        orderRepository.save(order);

    }
}
