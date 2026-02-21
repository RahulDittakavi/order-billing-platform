package com.rahul.order_billing_platform.entity;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@NotBlank
private String productName;

@NotNull
@Min(1)
private Integer quantity;

@NotNull
@Positive
private Double price;

private String status;
}
