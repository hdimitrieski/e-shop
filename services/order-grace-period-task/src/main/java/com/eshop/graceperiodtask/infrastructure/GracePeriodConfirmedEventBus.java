package com.eshop.graceperiodtask.infrastructure;

import com.eshop.shared.eventhandling.EventBus;
import com.eshop.shared.eventhandling.IntegrationEvent;
import com.eshop.graceperiodtask.config.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class GracePeriodConfirmedEventBus implements EventBus {
  private static final Logger logger = LoggerFactory.getLogger(GracePeriodConfirmedEventBus.class);
  private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;
  private final KafkaTopics topics;

  @Override
  public void publish(IntegrationEvent event) {
    logger.info("Publishing event: {} to kafka topic: {}", event.getClass().getSimpleName(), topics.getGracePeriodConfirmed());
    kafkaTemplate.send(topics.getGracePeriodConfirmed(), event);
  }
}
