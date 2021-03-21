package com.eshop.ordering.api.application.integrationevents;

import com.eshop.eventbus.IntegrationEvent;

public record LogEvent(IntegrationEvent event, String topic) {
}
