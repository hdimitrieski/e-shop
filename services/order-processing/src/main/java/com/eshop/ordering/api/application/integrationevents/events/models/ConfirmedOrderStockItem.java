package com.eshop.ordering.api.application.integrationevents.events.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConfirmedOrderStockItem {
  private Long productId;
  private boolean hasStock;

  public boolean hasStock() {
    return hasStock;
  }
}
