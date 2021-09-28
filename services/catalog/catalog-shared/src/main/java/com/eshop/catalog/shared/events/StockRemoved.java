package com.eshop.catalog.shared.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public class StockRemoved extends Event {
  private final Integer availableStock;

  public StockRemoved(UUID id, Integer availableStock) {
    super(id);
    this.availableStock = availableStock;
  }
}
