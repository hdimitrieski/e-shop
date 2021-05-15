package com.eshop.payment.infrastructure;

import com.eshop.shared.eventhandling.EventBus;
import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentStatusEventBus implements EventBus {
  private static final Logger logger = LoggerFactory.getLogger(PaymentStatusEventBus.class);

  private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;
  @Value("${spring.kafka.consumer.topic.paymentStatus}")
  private String paymentStatusTopic;

  @Override
  public void publish(IntegrationEvent event) {
    logger.info("Publishing integration event: {} - ({})", event.getId(), event.getClass().getSimpleName());
    kafkaTemplate.executeInTransaction(template ->
        template.send(paymentStatusTopic, event)
    );
  }
}
