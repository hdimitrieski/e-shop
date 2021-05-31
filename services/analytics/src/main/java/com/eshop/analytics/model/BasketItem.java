package com.eshop.analytics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasketItem {
  private Long productId;
  private String productName;
  private Integer quantity;
}
