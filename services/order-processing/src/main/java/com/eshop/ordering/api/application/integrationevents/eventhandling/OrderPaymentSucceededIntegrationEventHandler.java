package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.CancelOrderCommand;
import com.eshop.ordering.api.application.commands.SetPaidOrderStatusCommand;
import com.eshop.ordering.api.application.integrationevents.events.OrderPaymentStatusChangedIntegrationEvent;
import com.eshop.ordering.api.application.integrationevents.events.models.PaymentStatus;
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
  public void handle(OrderPaymentStatusChangedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

    if (PaymentStatus.SUCCESS.equals(event.getStatus())) {
      pipeline.send(new SetPaidOrderStatusCommand(event.getOrderId()));
    } else {
      logger.info("The payment of order {} failed. Cancelling the order.", event.getId());
      pipeline.send(new CancelOrderCommand(event.getOrderId()));
    }
  }
}
