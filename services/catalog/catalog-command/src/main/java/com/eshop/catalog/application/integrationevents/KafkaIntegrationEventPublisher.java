package com.eshop.catalog.application.integrationevents;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaIntegrationEventPublisher implements IntegrationEventPublisher {
  private static final Logger logger = LoggerFactory.getLogger(KafkaIntegrationEventPublisher.class);

  private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;

  @Override
  public void publish(String topic, IntegrationEvent event) {
    logger.info("Publishing integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    kafkaTemplate.send(topic, event);
  }

}
