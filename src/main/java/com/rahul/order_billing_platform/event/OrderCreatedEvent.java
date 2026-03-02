package com.rahul.order_billing_platform.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCreatedEvent {

    private Long orderId;
    private String productName;
    private Integer quantity;
    private Double price;
}
