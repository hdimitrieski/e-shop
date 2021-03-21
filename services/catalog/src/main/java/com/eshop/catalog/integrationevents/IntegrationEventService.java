package com.eshop.catalog.integrationevents;

import com.eshop.eventbus.IntegrationEvent;

public interface IntegrationEventService {
  void saveEventAndCatalogContextChanges(IntegrationEvent event);

  void publishThroughEventBus(String topic, IntegrationEvent event);
}
