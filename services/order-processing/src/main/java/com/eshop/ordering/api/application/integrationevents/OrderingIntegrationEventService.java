package com.eshop.ordering.api.application.integrationevents;

import com.eshop.eventbus.IntegrationEvent;

public interface OrderingIntegrationEventService {
  void publishEventsThroughEventBus(String transactionId);

  void addAndSaveEvent(String topic, IntegrationEvent evt);
}
