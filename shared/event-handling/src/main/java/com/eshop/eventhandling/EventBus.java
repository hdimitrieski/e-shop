package com.eshop.eventhandling;

public interface EventBus {
  void publish(IntegrationEvent event);
}
