package com.eshop.shared.eventhandling;

public interface EventBus {
  void publish(IntegrationEvent event);
}
