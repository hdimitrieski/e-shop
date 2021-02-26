package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.SetStockRejectedOrderStatusCommand;
import com.eshop.ordering.api.application.integrationevents.events.OrderStockRejectedIntegrationEvent;
import com.eshop.ordering.api.application.integrationevents.events.models.ConfirmedOrderStockItem;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderStockRejectedIntegrationEventHandler {
  private final Pipeline pipeline;

  @KafkaListener(groupId = "catalogGroup", topics = "${spring.kafka.consumer.topic.catalog}")
  public void handle(OrderStockRejectedIntegrationEvent event) {
    System.out.printf("----- Handling integration event: {%s} - (%s})", event.getId(), event.getClass().getSimpleName());
    var orderStockRejectedItems = event.getOrderStockItems().stream()
        .filter(c -> !c.hasStock())
        .map(ConfirmedOrderStockItem::productId)
        .collect(Collectors.toList());
    var command = new SetStockRejectedOrderStatusCommand(event.getOrderId(), orderStockRejectedItems);
    command.execute(pipeline);
  }
}
