package com.eshop.basket.infrastructure;

import com.eshop.eventbus.IntegrationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class OrderCheckoutsEventBus extends KafkaEventBus {
  public OrderCheckoutsEventBus(
      KafkaTemplate<String, IntegrationEvent> kafkaTemplate,
      @Value("${spring.kafka.consumer.topic.orderCheckouts}") String orderCheckoutsTopic
  ) {
    super(kafkaTemplate, orderCheckoutsTopic);
  }
}
