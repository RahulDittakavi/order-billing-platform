package com.rahul.order_billing_platform.controller;

import com.rahul.order_billing_platform.entity.Order;
import com.rahul.order_billing_platform.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.rahul.order_billing_platform.dto.OrderRequest;
import com.rahul.order_billing_platform.dto.OrderResponse;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

   @PostMapping
public ResponseEntity<OrderResponse> create(
        @Valid @RequestBody OrderRequest request) {
    return ResponseEntity.status(201)
            .body(orderService.createOrder(request));
}

@GetMapping
public ResponseEntity<List<OrderResponse>> getAll() {
    return ResponseEntity.ok(orderService.getAllOrders());
}

}
