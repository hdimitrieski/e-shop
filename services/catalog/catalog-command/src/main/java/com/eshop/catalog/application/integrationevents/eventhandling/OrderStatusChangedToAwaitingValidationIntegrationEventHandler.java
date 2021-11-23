package com.eshop.catalog.application.integrationevents.eventhandling;

import com.eshop.catalog.application.integrationevents.IntegrationEventPublisher;
import com.eshop.catalog.application.integrationevents.events.*;
import com.eshop.catalog.config.KafkaTopics;
import com.eshop.catalog.domain.catalogitem.CatalogItem;
import com.eshop.catalog.domain.catalogitem.CatalogItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToAwaitingValidationIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationIntegrationEventHandler.class);

  private final CatalogItemRepository catalogItemRepository;
  private final IntegrationEventPublisher integrationEventService;
  private final KafkaTopics kafkaTopics;

  @KafkaListener(
      groupId = "${app.kafka.group.ordersWaitingValidation}",
      topics = "${spring.kafka.consumer.topic.ordersWaitingForValidation}"
  )
  @Transactional
  public void handle(OrderStatusChangedToAwaitingValidationIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    var confirmedOrderStockItems = new ArrayList<ConfirmedOrderStockItem>();

    event.getOrderStockItems().forEach(orderStockItem -> catalogItemRepository
        .load(orderStockItem.getProductId())
        .ifPresent(catalogItem ->
            confirmedOrderStockItems.add(createConfirmedOrderStockItem(catalogItem, orderStockItem)))
    );

    if (allItemsAvailable(confirmedOrderStockItems)) {
      integrationEventService.publish(
          kafkaTopics.getOrderStockConfirmed(),
          new OrderStockConfirmedIntegrationEvent(event.getOrderId())
      );
    } else {
      integrationEventService.publish(kafkaTopics.getOrderStockRejected(),
          new OrderStockRejectedIntegrationEvent(event.getOrderId(), confirmedOrderStockItems));
    }

  }

  private ConfirmedOrderStockItem createConfirmedOrderStockItem(CatalogItem catalogItem, OrderStockItem orderStockItem) {
    return new ConfirmedOrderStockItem(catalogItem.getId(), hasStock(catalogItem, orderStockItem));
  }

  private boolean hasStock(CatalogItem catalogItem, OrderStockItem orderStockItem) {
    return catalogItem.getAvailableStock().getValue() >= orderStockItem.getUnits();
  }

  private boolean allItemsAvailable(List<ConfirmedOrderStockItem> confirmedOrderStockItems) {
    return confirmedOrderStockItems.stream().allMatch(ConfirmedOrderStockItem::getHasStock);
  }

}
