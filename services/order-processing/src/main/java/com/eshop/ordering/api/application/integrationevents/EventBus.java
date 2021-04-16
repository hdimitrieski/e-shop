package com.eshop.ordering.api.application.integrationevents;

import com.eshop.eventbus.IntegrationEvent;

// TODO HD move in shared project (BuildingBlocks)
public interface EventBus {
  void publish(String topic, IntegrationEvent event);
}
