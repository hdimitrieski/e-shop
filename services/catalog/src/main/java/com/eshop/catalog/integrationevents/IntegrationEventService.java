package com.eshop.catalog.integrationevents;

import com.eshop.eventbus.IntegrationEvent;

public interface IntegrationEventService {
  void saveEventAndCatalogContextChanges(String topic, IntegrationEvent event);

  void publishThroughEventBus(String topic, IntegrationEvent event);
}
