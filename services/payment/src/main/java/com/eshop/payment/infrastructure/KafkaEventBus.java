package com.eshop.payment.infrastructure;

import com.eshop.payment.events.IntegrationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventBus implements EventBus {

  private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;
  private final String paymentTopic;

  public KafkaEventBus(
      KafkaTemplate<String, IntegrationEvent> kafkaTemplate,
      @Value("${spring.kafka.consumer.topic.payment}") String paymentTopic
  ) {
    this.kafkaTemplate = kafkaTemplate;
    this.paymentTopic = paymentTopic;
  }

  @Override
  public void publish(IntegrationEvent event) {
    kafkaTemplate.send(paymentTopic, event);
  }
}
