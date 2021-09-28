package com.eshop.ordering.api.application.integrationevents.events.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConfirmedOrderStockItem {
  private UUID productId;
  private boolean hasStock;

  public boolean hasStock() {
    return hasStock;
  }
}
