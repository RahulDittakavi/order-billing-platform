package com.rahul.order_billing_platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

    private Long id;
    private String productName;
    private Integer quantity;
    private Double price;
    private String status;
}
