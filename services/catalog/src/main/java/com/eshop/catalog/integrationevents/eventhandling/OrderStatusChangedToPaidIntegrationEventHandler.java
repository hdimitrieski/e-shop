package com.eshop.catalog.integrationevents.eventhandling;

import com.eshop.catalog.integrationevents.events.OrderStatusChangedToPaidIntegrationEvent;
import com.eshop.catalog.model.CatalogItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@AllArgsConstructor
@Component
public class OrderStatusChangedToPaidIntegrationEventHandler {
  private final CatalogItemRepository catalogItemRepository;

  @KafkaListener(groupId = "paid-orders-group", topics = "${spring.kafka.consumer.topic.paidOrders}")
  @Transactional
  public void handle(OrderStatusChangedToPaidIntegrationEvent event) {
    System.out.printf("----- Handling integration event: %s (%s)", event.getId(), event.getClass().getSimpleName());
    for (var orderStockItem : event.getOrderStockItems()) {
      catalogItemRepository.findById(orderStockItem.getProductId()).ifPresent(catalogItem ->
          catalogItem.removeStock(orderStockItem.getUnits())
      );
    }
  }
}
