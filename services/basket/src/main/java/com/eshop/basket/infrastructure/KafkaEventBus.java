package com.eshop.basket.infrastructure;

import com.eshop.basket.shared.IntegrationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventBus implements EventBus {

  private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;

  public KafkaEventBus(
      KafkaTemplate<String, IntegrationEvent> kafkaTemplate
  ) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void publish(String topic, IntegrationEvent event) {
    kafkaTemplate.send(topic, event);
  }
}
