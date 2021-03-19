package com.eshop.catalog.integrationevents;

import com.eshop.catalog.shared.IntegrationEvent;

// TODO HD move to shared maven module
public interface IntegrationEventService {
    void saveEventAndCatalogContextChanges(IntegrationEvent event);
    void publishThroughEventBus(String topic, IntegrationEvent event);
}
