package com.eshop.catalog.integrationevents.eventhandling;

import com.eshop.catalog.integrationevents.IntegrationEventService;
import com.eshop.catalog.integrationevents.events.ConfirmedOrderStockItem;
import com.eshop.catalog.integrationevents.events.OrderStatusChangedToAwaitingValidationIntegrationEvent;
import com.eshop.catalog.integrationevents.events.OrderStockConfirmedIntegrationEvent;
import com.eshop.catalog.integrationevents.events.OrderStockRejectedIntegrationEvent;
import com.eshop.catalog.model.CatalogItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToAwaitingValidationIntegrationEventHandler {
  private final CatalogItemRepository catalogItemRepository;
  private final IntegrationEventService integrationEventService;
  @Value("${spring.kafka.consumer.topic.orderStockStatuses}")
  private String orderStockStatusesTopic;

  @KafkaListener(groupId = "orders-waiting-validation-group", topics = "${spring.kafka.consumer.topic.ordersWaitingForValidation}")
  public void handle(OrderStatusChangedToAwaitingValidationIntegrationEvent event) {
    System.out.printf("----- Handling integration event: %s (%s)", event.getId(), event.getClass().getSimpleName());
    var confirmedOrderStockItems = new ArrayList<ConfirmedOrderStockItem>();

    for (var orderStockItem : event.getOrderStockItems()) {
      catalogItemRepository.findById(orderStockItem.getProductId()).ifPresent(catalogItem -> {
        var hasStock = catalogItem.getAvailableStock() >= orderStockItem.getUnits();
        var confirmedStockItem = new ConfirmedOrderStockItem(catalogItem.getId(), hasStock);
        confirmedOrderStockItems.add(confirmedStockItem);
      });
    }

    var confirmedIntegrationEvent = confirmedOrderStockItems.stream()
        .anyMatch(c -> !c.getHasStock())
        ? new OrderStockRejectedIntegrationEvent(event.getOrderId(), confirmedOrderStockItems)
        : new OrderStockConfirmedIntegrationEvent(event.getOrderId());

    integrationEventService.saveEventAndCatalogContextChanges(confirmedIntegrationEvent);
    integrationEventService.publishThroughEventBus(orderStockStatusesTopic, confirmedIntegrationEvent);
  }
}
