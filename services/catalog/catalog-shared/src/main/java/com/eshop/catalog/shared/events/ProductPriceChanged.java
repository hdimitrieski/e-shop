package com.eshop.catalog.shared.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductPriceChanged extends Event {
  private Double price;

  public ProductPriceChanged(UUID id, Double price) {
    super(id);
    this.price = price;
  }
}
