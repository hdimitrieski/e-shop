package com.eshop.ordering.api.application.integrationevents;

import com.eshop.shared.eventhandling.IntegrationEvent;
import com.eshop.shared.outbox.IntegrationEventLogEntry;
import com.eshop.shared.outbox.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaEventBus implements IntegrationEventPublisher {
  private static final Logger logger = LoggerFactory.getLogger(IntegrationEventPublisher.class);

  private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;

  @Override
  public void publish(IntegrationEventLogEntry eventLogEntry) {
    var event = eventLogEntry.getEvent();
    logger.info("Publishing integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    kafkaTemplate.send(eventLogEntry.getTopic(), event);
  }
}
