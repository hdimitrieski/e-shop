package com.eshop.basket.infrastructure;

import com.eshop.basket.shared.IntegrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventBus implements EventBus {
  private static final Logger logger = LoggerFactory.getLogger(KafkaEventBus.class);

  private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;

  public KafkaEventBus(
      KafkaTemplate<String, IntegrationEvent> kafkaTemplate
  ) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void publish(String topic, IntegrationEvent event) {
    logger.info("Publishing event: {} on kafka topic: {}", event.getClass().getSimpleName(), topic);
    kafkaTemplate.send(topic, event);
  }
}
