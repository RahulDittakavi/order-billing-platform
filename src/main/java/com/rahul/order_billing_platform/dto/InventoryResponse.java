package com.rahul.order_billing_platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {

    private Long id;
    private String productName;
    private Integer availableQuantity;
}
