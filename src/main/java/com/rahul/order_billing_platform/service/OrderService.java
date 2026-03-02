package com.rahul.order_billing_platform.service;

import com.rahul.order_billing_platform.entity.Inventory;
import com.rahul.order_billing_platform.entity.Order;
import com.rahul.order_billing_platform.event.OrderCreatedEvent;
import com.rahul.order_billing_platform.exception.InsufficientInventoryException;
import com.rahul.order_billing_platform.kafka.OrderEventProducer;
import com.rahul.order_billing_platform.repository.InventoryRepository;
import com.rahul.order_billing_platform.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.rahul.order_billing_platform.dto.OrderRequest;
import com.rahul.order_billing_platform.dto.OrderResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderEventProducer orderEventProducer;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {

        Inventory inventory = inventoryRepository
                .findByProductName(request.getProductName())
                .orElseThrow(() -> new InsufficientInventoryException("Product not found in inventory"));

        if (inventory.getAvailableQuantity() < request.getQuantity()) {
            throw new InsufficientInventoryException("Insufficient stock available");
        }

        // reduce stock
        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - request.getQuantity());
        inventoryRepository.save(inventory);

        Order order = new Order();
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        order.setStatus("CREATED");

        Order saved = orderRepository.save(order);
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(saved.getId())
                .productName(saved.getProductName())
                .quantity(saved.getQuantity())
                .price(saved.getPrice())
                .build();

        orderEventProducer.sendOrderCreatedEvent(event);

        return mapToResponse(saved);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .productName(order.getProductName())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .status(order.getStatus())
                .build();
    }

}
