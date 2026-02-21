package com.rahul.order_billing_platform.repository;

import com.rahul.order_billing_platform.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
