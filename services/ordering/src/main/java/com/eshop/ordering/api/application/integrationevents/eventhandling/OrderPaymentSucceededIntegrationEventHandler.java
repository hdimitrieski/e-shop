package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.SetPaidOrderStatusCommand;
import com.eshop.ordering.api.application.integrationevents.events.OrderPaymentSucceededIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPaymentSucceededIntegrationEventHandler {
  private final Pipeline pipeline;

  @KafkaListener(groupId = "paymentGroup", topics = "${spring.kafka.consumer.topic.payment}")
  public void handle(OrderPaymentSucceededIntegrationEvent event) {
    System.out.printf("----- Handling integration event: {%s} - (%s})", event.getId(), event.getClass().getSimpleName());

    pipeline.send(new SetPaidOrderStatusCommand(event.getOrderId()));
  }
}
