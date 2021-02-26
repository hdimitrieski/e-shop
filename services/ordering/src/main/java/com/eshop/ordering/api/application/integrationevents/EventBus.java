package com.eshop.ordering.api.application.integrationevents;

public interface EventBus {
    void publish(IntegrationEvent event);
}
