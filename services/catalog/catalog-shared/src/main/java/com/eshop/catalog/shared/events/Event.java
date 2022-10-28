package com.eshop.catalog.shared.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public abstract class Event {
  private UUID id;
  private LocalDateTime occurredOn = LocalDateTime.now();

  public Event(UUID id) {
    this.id = id;
  }

}
