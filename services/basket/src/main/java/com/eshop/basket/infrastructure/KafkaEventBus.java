package com.eshop.basket.infrastructure;

import com.eshop.shared.eventhandling.EventBus;
import com.eshop.shared.eventhandling.IntegrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

class KafkaEventBus implements EventBus {
  private static final Logger logger = LoggerFactory.getLogger(KafkaEventBus.class);
  private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;
  private final String topic;

  public KafkaEventBus(
      KafkaTemplate<String, IntegrationEvent> kafkaTemplate,
      String topic
  ) {
    this.kafkaTemplate = kafkaTemplate;
    this.topic = topic;
  }

  @Override
  public void publish(IntegrationEvent event) {
    logger.info("Publishing event: {} to kafka topic: {}", event.getClass().getSimpleName(), topic);
    kafkaTemplate.send(topic, event);
  }
}
