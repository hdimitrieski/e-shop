package com.eshop.ordering.api.application.integrationevents;

// TODO HD move in shared project (BuildingBlocks)
public interface EventBus {
  void publish(IntegrationEvent event);
}
