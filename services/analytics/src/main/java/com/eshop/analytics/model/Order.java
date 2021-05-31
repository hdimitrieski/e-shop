package com.eshop.analytics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order {
  private Long id;
  private Double totalPrice;
  private List<OrderItem> orderItems;
}
