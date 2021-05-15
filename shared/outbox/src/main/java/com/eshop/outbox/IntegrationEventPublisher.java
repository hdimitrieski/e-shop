package com.eshop.outbox;

public interface IntegrationEventPublisher {
  void publish(IntegrationEventLogEntry eventLogEntry);
}
