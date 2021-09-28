package com.eshop.catalog.shared.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public class BrandAdded extends Event {
  private final String name;

  public BrandAdded(UUID id, String name) {
    super(id);
    this.name = name;
  }
}
