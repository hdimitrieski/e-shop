package com.eshop.catalog.shared.events;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class Event {
  private final UUID id;
  private final LocalDateTime occurredOn = LocalDateTime.now();

  public Event(UUID id) {
    this.id = id;
  }

}
