package com.eshop.ordering.api.application.integrationevents;

public record LogEvent(IntegrationEvent event, String topic) {
}
