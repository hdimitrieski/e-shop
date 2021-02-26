package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.CancelOrderCommand;
import com.eshop.ordering.api.application.integrationevents.events.OrderPaymentFailedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPaymentFailedIntegrationEventHandler {
  private final Pipeline pipeline;

  @KafkaListener(groupId = "catalogGroup", topics = "${spring.kafka.consumer.topic.payment}")
  public void handle(OrderPaymentFailedIntegrationEvent event) {
    System.out.printf("----- Handling integration event: {%s} - (%s})", event.getId(), event.getClass().getSimpleName());
    var command = new CancelOrderCommand(event.getOrderId());

    command.execute(pipeline);
  }
}
