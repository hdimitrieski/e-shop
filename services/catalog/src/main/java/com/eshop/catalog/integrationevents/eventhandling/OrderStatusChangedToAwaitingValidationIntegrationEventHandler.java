package com.eshop.catalog.integrationevents.eventhandling;

import com.eshop.catalog.integrationevents.IntegrationEventService;
import com.eshop.catalog.integrationevents.events.*;
import com.eshop.catalog.model.CatalogItem;
import com.eshop.catalog.model.CatalogItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToAwaitingValidationIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationIntegrationEventHandler.class);

  private final CatalogItemRepository catalogItemRepository;
  private final IntegrationEventService integrationEventService;
  @Value("${spring.kafka.consumer.topic.orderStockConfirmed}")
  private String orderStockConfirmedTopic;
  @Value("${spring.kafka.consumer.topic.orderStockRejected}")
  private String orderStockRejectedTopic;

  @KafkaListener(
      groupId = "orders-waiting-validation-group",
      topics = "${spring.kafka.consumer.topic.ordersWaitingForValidation}"
  )
  public void handle(OrderStatusChangedToAwaitingValidationIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    var confirmedOrderStockItems = new ArrayList<ConfirmedOrderStockItem>();

    event.getOrderStockItems().forEach(orderStockItem -> catalogItemRepository
        .findById(orderStockItem.getProductId())
        .ifPresent(catalogItem ->
            confirmedOrderStockItems.add(createConfirmedOrderStockItem(catalogItem, orderStockItem)))
    );

    if (allItemsAvailable(confirmedOrderStockItems)) {
      integrationEventService.saveEventAndCatalogContextChanges(
          orderStockConfirmedTopic,
          new OrderStockConfirmedIntegrationEvent(event.getOrderId())
      );
    } else {
      integrationEventService.saveEventAndCatalogContextChanges(orderStockRejectedTopic,
          new OrderStockRejectedIntegrationEvent(event.getOrderId(), confirmedOrderStockItems));
    }

  }

  private ConfirmedOrderStockItem createConfirmedOrderStockItem(CatalogItem catalogItem, OrderStockItem orderStockItem) {
    return new ConfirmedOrderStockItem(catalogItem.getId(), hasStock(catalogItem, orderStockItem));
  }

  private boolean hasStock(CatalogItem catalogItem, OrderStockItem orderStockItem) {
    return catalogItem.getAvailableStock() >= orderStockItem.getUnits();
  }

  private boolean allItemsAvailable(List<ConfirmedOrderStockItem> confirmedOrderStockItems) {
    return confirmedOrderStockItems.stream().allMatch(ConfirmedOrderStockItem::getHasStock);
  }

}
