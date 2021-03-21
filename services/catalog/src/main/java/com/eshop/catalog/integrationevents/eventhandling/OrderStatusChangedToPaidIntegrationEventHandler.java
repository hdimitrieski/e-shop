package com.eshop.catalog.integrationevents.eventhandling;

import com.eshop.catalog.integrationevents.events.OrderStatusChangedToPaidIntegrationEvent;
import com.eshop.catalog.model.CatalogItemRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@AllArgsConstructor
@Component
public class OrderStatusChangedToPaidIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationIntegrationEventHandler.class);

  private final CatalogItemRepository catalogItemRepository;

  @KafkaListener(groupId = "paid-orders-group", topics = "${spring.kafka.consumer.topic.paidOrders}")
  @Transactional
  public void handle(OrderStatusChangedToPaidIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    for (var orderStockItem : event.getOrderStockItems()) {
      catalogItemRepository.findById(orderStockItem.getProductId()).ifPresent(catalogItem ->
          catalogItem.removeStock(orderStockItem.getUnits())
      );
    }
  }
}
