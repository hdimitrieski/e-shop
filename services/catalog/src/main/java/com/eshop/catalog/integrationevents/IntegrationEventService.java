package com.eshop.catalog.integrationevents;

import com.eshop.catalog.shared.IntegrationEvent;

// TODO HD move to shared maven module
public interface IntegrationEventService {
    void SaveEventAndCatalogContextChanges(IntegrationEvent event);
    void PublishThroughEventBus(IntegrationEvent event);
}
