package com.eshop.basket.infrastructure;

import com.eshop.basket.config.KafkaTopics;
import com.eshop.shared.eventhandling.IntegrationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class OrderCheckoutsEventBus extends KafkaEventBus {

  public OrderCheckoutsEventBus(
      KafkaTemplate<String, IntegrationEvent> kafkaTemplate,
      KafkaTopics topics
  ) {
    super(kafkaTemplate, topics.getOrderCheckouts());
  }
}
