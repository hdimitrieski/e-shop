package com.eshop.ordering.api.application.integrationevents.events.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStockItem {
  private Integer productId;
  private Integer units;
}
