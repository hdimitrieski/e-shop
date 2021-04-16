package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.SetPaidOrderStatusCommand;
import com.eshop.ordering.api.application.integrationevents.events.OrderPaymentSucceededIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPaymentSucceededIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderPaymentSucceededIntegrationEventHandler.class);

  private final Pipeline pipeline;

  @KafkaListener(groupId = "payment-status-group", topics = "${spring.kafka.consumer.topic.paymentStatus}")
  public void handle(OrderPaymentSucceededIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

    pipeline.send(new SetPaidOrderStatusCommand(event.getOrderId()));
  }
}
