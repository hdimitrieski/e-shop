package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.SetStockConfirmedOrderStatusCommand;
import com.eshop.ordering.api.application.integrationevents.events.OrderStockConfirmedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStockConfirmedIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStockConfirmedIntegrationEventHandler.class);

  private final Pipeline pipeline;

  @KafkaListener(groupId = "order-stock-statuses-group", topics = "${spring.kafka.consumer.topic.orderStockStatuses}")
  public void handle(OrderStockConfirmedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

    pipeline.send(new SetStockConfirmedOrderStatusCommand(event.getOrderId()));
  }

  @KafkaListener(groupId = "order-stock-statuses-group-dlt", topics = "${spring.kafka.consumer.topic.orderStockStatuses}.DLT")
  public void handleDlt(OrderStockConfirmedIntegrationEvent event) {
    logger.info("DLT - Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
  }
}
