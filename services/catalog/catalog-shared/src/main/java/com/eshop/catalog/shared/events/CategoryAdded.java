package com.eshop.catalog.shared.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class CategoryAdded extends Event {
  private String name;

  public CategoryAdded(UUID id, String name) {
    super(id);
    this.name = name;
  }
}
