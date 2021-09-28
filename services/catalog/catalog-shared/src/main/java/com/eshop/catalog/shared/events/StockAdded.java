package com.eshop.catalog.shared.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public class StockAdded extends Event {
  private final Integer availableStock;

  public StockAdded(UUID id, Integer availableStock) {
    super(id);
    this.availableStock = availableStock;
  }
}
