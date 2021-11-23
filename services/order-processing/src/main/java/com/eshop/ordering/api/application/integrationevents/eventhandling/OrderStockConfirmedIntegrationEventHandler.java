package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.SetStockConfirmedOrderStatusCommand;
import com.eshop.ordering.api.application.integrationevents.events.OrderStockConfirmedIntegrationEvent;
import com.eshop.ordering.shared.EventHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

@EventHandler
@RequiredArgsConstructor
public class OrderStockConfirmedIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStockConfirmedIntegrationEventHandler.class);

  private final Pipeline pipeline;

  @KafkaListener(
      groupId = "${app.kafka.group.orderStockConfirmed}",
      topics = "${spring.kafka.consumer.topic.orderStockConfirmed}"
  )
  public void handle(OrderStockConfirmedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

    pipeline.send(new SetStockConfirmedOrderStatusCommand(event.getOrderId()));
  }

  @KafkaListener(
      groupId = "${app.kafka.group.orderStockConfirmed}-dlt",
      topics = "${spring.kafka.consumer.topic.orderStockConfirmed}.DLT"
  )
  public void handleDlt(OrderStockConfirmedIntegrationEvent event) {
    logger.info("DLT - Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
  }
}
