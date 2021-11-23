package com.eshop.catalog.application.integrationevents.eventhandling;

import com.eshop.catalog.application.integrationevents.events.OrderStatusChangedToPaidIntegrationEvent;
import com.eshop.catalog.domain.catalogitem.CatalogItemRepository;
import com.eshop.catalog.domain.catalogitem.Units;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderStatusChangedToPaidIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationIntegrationEventHandler.class);

  private final CatalogItemRepository catalogItemRepository;

  @KafkaListener(groupId = "${app.kafka.group.paidOrders}", topics = "${spring.kafka.consumer.topic.paidOrders}")
  public void handle(OrderStatusChangedToPaidIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    event.getOrderStockItems().forEach(orderStockItem -> catalogItemRepository
        .load(orderStockItem.getProductId())
        .ifPresent(catalogItem ->
            catalogItem.removeStock(Units.of(orderStockItem.getUnits()))
        )
    );
  }
}
