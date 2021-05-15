package com.eshop.shared.eventhandling;

public interface IntegrationEventHandler<T> {
  void handle(T event);
}
