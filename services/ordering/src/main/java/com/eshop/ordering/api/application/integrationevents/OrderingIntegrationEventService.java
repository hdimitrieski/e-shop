package com.eshop.ordering.api.application.integrationevents;

import java.util.UUID;

public interface OrderingIntegrationEventService {
    void publishEventsThroughEventBus(UUID transactionId);
    void addAndSaveEvent(IntegrationEvent evt);
}
