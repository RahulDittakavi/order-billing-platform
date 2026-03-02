package com.rahul.order_billing_platform.controller;

import com.rahul.order_billing_platform.dto.InventoryRequest;
import com.rahul.order_billing_platform.dto.InventoryResponse;
import com.rahul.order_billing_platform.dto.InventoryUpdateRequest;
import com.rahul.order_billing_platform.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> add(
            @Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.addInventory(request));
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAll() {
        return ResponseEntity.ok(inventoryService.getAll());
    }

    @PatchMapping("/{productName}/increase")
    public ResponseEntity<InventoryResponse> increase(
            @PathVariable String productName,
            @Valid @RequestBody InventoryUpdateRequest request) {

        return ResponseEntity.ok(
                inventoryService.increaseStock(productName, request.getQuantity()));
    }

}
