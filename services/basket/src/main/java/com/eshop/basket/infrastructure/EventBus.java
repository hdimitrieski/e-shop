package com.eshop.basket.infrastructure;

import com.eshop.basket.shared.IntegrationEvent;

public interface EventBus {
  void publish(String topic, IntegrationEvent event);
}
