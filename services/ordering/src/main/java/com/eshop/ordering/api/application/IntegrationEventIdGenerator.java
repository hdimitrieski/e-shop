package com.eshop.ordering.api.application;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.transaction.TransactionScoped;
import java.util.UUID;

import static java.util.Objects.isNull;

@Component
@TransactionScoped
public class IntegrationEventIdGenerator {
  private UUID eventId;

  public UUID transactionId() {
    if (isNull(eventId)) {
      eventId = UUID.randomUUID();
    }
    return eventId;
  }

}
