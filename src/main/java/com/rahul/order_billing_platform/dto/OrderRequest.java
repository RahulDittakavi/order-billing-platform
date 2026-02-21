package com.rahul.order_billing_platform.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderRequest {

    @NotBlank
    private String productName;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    @Positive
    private Double price;
}
