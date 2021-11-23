package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.SetAwaitingValidationOrderStatusCommand;
import com.eshop.ordering.api.application.integrationevents.events.GracePeriodConfirmedIntegrationEvent;
import com.eshop.ordering.shared.EventHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

@EventHandler
@RequiredArgsConstructor
public class GracePeriodConfirmedIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(GracePeriodConfirmedIntegrationEventHandler.class);

  private final Pipeline pipeline;

  /**
   * Event handler which confirms that the grace period has been completed and order will not initially be cancelled.
   * Therefore, the order process continues for validation.
   */
  @KafkaListener(
      groupId = "${app.kafka.group.gracePeriodConfirmed}",
      topics = "${spring.kafka.consumer.topic.gracePeriodConfirmed}"
  )
  public void handle(GracePeriodConfirmedIntegrationEvent event) {
    logger.info("Handling integration event: {} - ({})", event.getId(), event.getClass().getSimpleName());

    pipeline.send(new SetAwaitingValidationOrderStatusCommand(event.getOrderId()));
  }
}
