package com.eshop.basket.infrastructure;

import com.eshop.basket.shared.IntegrationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventBus implements EventBus {

  private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;
  private final String basketTopic;

  public KafkaEventBus(
      KafkaTemplate<String, IntegrationEvent> kafkaTemplate,
      @Value("${spring.kafka.consumer.topic.basket}") String basketTopic
  ) {
    this.kafkaTemplate = kafkaTemplate;
    this.basketTopic = basketTopic;
  }

  @Override
  public void publish(IntegrationEvent event) {
    kafkaTemplate.send(basketTopic, event);
  }
}
