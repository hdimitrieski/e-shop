package com.eshop.ordering.api.application.domaineventhandlers.orderpaid;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToPaidIntegrationEvent;
import com.eshop.ordering.api.application.integrationevents.events.models.OrderStockItem;
import com.eshop.ordering.api.application.services.OrderApplicationService;
import com.eshop.ordering.config.KafkaTopics;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderStatus;
import com.eshop.ordering.domain.events.OrderStatusChangedToPaidDomainEvent;
import com.eshop.ordering.shared.EventHandler;
import com.eshop.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import java.util.stream.Collectors;

@EventHandler
@RequiredArgsConstructor
public class OrderStatusChangedToPaidDomainEventHandler implements DomainEventHandler<OrderStatusChangedToPaidDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToPaidDomainEventHandler.class);

  private final OrderApplicationService orderApplicationService;
  private final IntegrationEventLogService integrationEventLogService;
  private final KafkaTopics topics;

  @EventListener
  public void handle(OrderStatusChangedToPaidDomainEvent event) {
    logger.info(
        "Order with Id: {} has been successfully updated to status {}",
        event.orderId(),
        OrderStatus.Paid
    );

    var order = orderApplicationService.findOrder(event.orderId());
    var buyer = orderApplicationService.findBuyerFor(order);

    var orderStockList = event.orderItems().stream()
        .map(orderItem -> new OrderStockItem(orderItem.getProductId(), orderItem.getUnits().getValue()))
        .collect(Collectors.toList());

    var orderStatusChangedToPaidIntegrationEvent = new OrderStatusChangedToPaidIntegrationEvent(
        event.orderId().getUuid(),
        order.getOrderStatus().getStatus(),
        buyer.getBuyerName().getName(),
        orderStockList);

    integrationEventLogService.saveEvent(orderStatusChangedToPaidIntegrationEvent, topics.getPaidOrders());
  }
}
