package com.eshop.payment.infrastructure;

import com.eshop.payment.events.IntegrationEvent;
import org.springframework.beans.factory.annotation.Value;
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
