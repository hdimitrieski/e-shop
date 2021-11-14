package com.eshop.catalogquery.application.integrationevents;

import com.eshop.shared.eventhandling.IntegrationEvent;

public interface IntegrationEventPublisher {
  void publish(String topic, IntegrationEvent event);
}
