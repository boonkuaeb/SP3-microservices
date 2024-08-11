package com.bk.microservice.order.repositry;

import com.bk.microservice.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
