package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.CancelOrderCommand;
import com.eshop.ordering.api.application.integrationevents.events.OrderPaymentFailedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPaymentFailedIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderPaymentFailedIntegrationEventHandler.class);

  private final Pipeline pipeline;

  @KafkaListener(groupId = "payment-status-group", topics = "${spring.kafka.consumer.topic.paymentStatus}")
  public void handle(OrderPaymentFailedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

    pipeline.send(new CancelOrderCommand(event.getOrderId()));
  }
}
