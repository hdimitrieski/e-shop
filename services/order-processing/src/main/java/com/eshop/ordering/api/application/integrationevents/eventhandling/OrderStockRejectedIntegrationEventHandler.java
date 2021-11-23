package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.SetStockRejectedOrderStatusCommand;
import com.eshop.ordering.api.application.integrationevents.events.OrderStockRejectedIntegrationEvent;
import com.eshop.ordering.api.application.integrationevents.events.models.ConfirmedOrderStockItem;
import com.eshop.ordering.shared.EventHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.stream.Collectors;

@EventHandler
@RequiredArgsConstructor
public class OrderStockRejectedIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStockRejectedIntegrationEventHandler.class);

  private final Pipeline pipeline;

  @KafkaListener(
      groupId = "${app.kafka.group.orderStockRejected}",
      topics = "${spring.kafka.consumer.topic.orderStockRejected}"
  )
  public void handle(OrderStockRejectedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    var orderStockRejectedItems = event.getOrderStockItems().stream()
        .filter(c -> !c.hasStock())
        .map(ConfirmedOrderStockItem::getProductId)
        .collect(Collectors.toList());
    pipeline.send(new SetStockRejectedOrderStatusCommand(event.getOrderId(), orderStockRejectedItems));
  }
}
