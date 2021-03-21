package com.eshop.eventbus;

public interface IntegrationEventHandler<T> {
  void handle(T event);
}
