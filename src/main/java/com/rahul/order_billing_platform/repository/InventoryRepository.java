package com.rahul.order_billing_platform.repository;

import com.rahul.order_billing_platform.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductName(String productName);
}
