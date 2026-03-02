package com.rahul.order_billing_platform.service;

import com.rahul.order_billing_platform.dto.InventoryRequest;
import com.rahul.order_billing_platform.dto.InventoryResponse;
import com.rahul.order_billing_platform.entity.Inventory;
import com.rahul.order_billing_platform.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rahul.order_billing_platform.exception.InsufficientInventoryException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryResponse addInventory(InventoryRequest request) {

        inventoryRepository.findByProductName(request.getProductName())
                .ifPresent(inv -> {
                    throw new RuntimeException("Product already exists in inventory");
                });

        Inventory inventory = new Inventory();
        inventory.setProductName(request.getProductName());
        inventory.setAvailableQuantity(request.getAvailableQuantity());

        Inventory saved = inventoryRepository.save(inventory);

        return map(saved);
    }

    public List<InventoryResponse> getAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    private InventoryResponse map(Inventory inv) {
        return InventoryResponse.builder()
                .id(inv.getId())
                .productName(inv.getProductName())
                .availableQuantity(inv.getAvailableQuantity())
                .build();
    }

    @Transactional
    public InventoryResponse increaseStock(String productName, Integer quantity) {

        Inventory inventory = inventoryRepository
                .findByProductName(productName)
                .orElseThrow(() -> new InsufficientInventoryException("Product not found"));

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() + quantity);

        Inventory saved = inventoryRepository.save(inventory);

        return map(saved);
    }
}
