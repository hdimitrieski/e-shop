package com.eshop.catalog.shared.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class StockAdded extends Event {
  private Integer availableStock;

  public StockAdded(UUID id, Integer availableStock) {
    super(id);
    this.availableStock = availableStock;
  }
}
