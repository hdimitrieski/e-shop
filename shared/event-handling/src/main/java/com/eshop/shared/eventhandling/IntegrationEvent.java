package com.eshop.shared.eventhandling;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * An Integration Event is an event that can cause side effects to other microservices, Bounded-Contexts or external systems.
 */
@RequiredArgsConstructor
@Getter
public class IntegrationEvent {
  private final UUID id;
  private final LocalDateTime creationDate;

  public IntegrationEvent() {
    this(UUID.randomUUID(), LocalDateTime.now());
  }
}
