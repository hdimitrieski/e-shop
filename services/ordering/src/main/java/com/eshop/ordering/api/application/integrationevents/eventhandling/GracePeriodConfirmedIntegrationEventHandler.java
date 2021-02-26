package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.SetAwaitingValidationOrderStatusCommand;
import com.eshop.ordering.api.application.integrationevents.events.GracePeriodConfirmedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GracePeriodConfirmedIntegrationEventHandler {
  private final Pipeline pipeline;

  /**
   * Event handler which confirms that the grace period has been completed and order will not initially be cancelled.
   * Therefore, the order process continues for validation.
   */
  @KafkaListener(groupId = "catalogGroup", topics = "${spring.kafka.consumer.topic.catalog}")
  public void handle(GracePeriodConfirmedIntegrationEvent event) {
    System.out.printf("----- Handling integration event: {%s} - (%s})", event.getId(), event.getClass().getSimpleName());

    var command = new SetAwaitingValidationOrderStatusCommand(event.getOrderId());
    command.execute(pipeline);
  }
}
