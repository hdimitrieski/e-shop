package com.eshop.shared.outbox;

public interface IntegrationEventPublisher {
  void publish(IntegrationEventLogEntry eventLogEntry);
}
