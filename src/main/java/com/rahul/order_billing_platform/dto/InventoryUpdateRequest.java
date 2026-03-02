package com.rahul.order_billing_platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryUpdateRequest {

    @NotNull
    @Min(1)
    private Integer quantity;
}
