package com.eshop.catalog.shared.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CategoryAdded extends Event {
  private final String name;

  public CategoryAdded(UUID id, String name) {
    super(id);
    this.name = name;
  }
}
