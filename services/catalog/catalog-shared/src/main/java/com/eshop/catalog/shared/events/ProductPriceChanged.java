package com.eshop.catalog.shared.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductPriceChanged extends Event {
  private final Double price;

  public ProductPriceChanged(UUID id, Double price) {
    super(id);
    this.price = price;
  }
}
