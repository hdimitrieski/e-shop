package com.eshop.ordering.api.application.integrationevents;

public interface OrderingIntegrationEventService {
    void publishEventsThroughEventBus(long transactionId);
    void addAndSaveEvent(String topic, IntegrationEvent evt);
}
