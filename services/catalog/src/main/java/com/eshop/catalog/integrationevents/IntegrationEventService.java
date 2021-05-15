package com.eshop.catalog.integrationevents;

import com.eshop.shared.eventhandling.IntegrationEvent;

public interface IntegrationEventService {
  void saveEventAndCatalogContextChanges(String topic, IntegrationEvent event);
}
