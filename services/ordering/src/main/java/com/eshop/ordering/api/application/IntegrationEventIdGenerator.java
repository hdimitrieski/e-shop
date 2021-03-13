package com.eshop.ordering.api.application;

import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Objects.isNull;

@Component
public class IntegrationEventIdGenerator {
  // TODO HD i need new solution for this
  private UUID eventId;

  public UUID transactionId() {
    if (isNull(eventId)) {
      eventId = UUID.randomUUID();
    }
    return eventId;
  }

}
