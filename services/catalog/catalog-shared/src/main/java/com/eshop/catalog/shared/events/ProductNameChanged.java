package com.eshop.catalog.shared.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductNameChanged extends Event {
  private final String name;

  public ProductNameChanged(UUID id, String name) {
    super(id);
    this.name = name;
  }
}
