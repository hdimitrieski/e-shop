package com.eshop.ordering.api.application.domaineventhandlers.ordershipped;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToShippedIntegrationEvent;
import com.eshop.ordering.api.application.services.OrderApplicationService;
import com.eshop.ordering.config.KafkaTopics;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderStatus;
import com.eshop.ordering.domain.events.OrderShippedDomainEvent;
import com.eshop.ordering.shared.EventHandler;
import com.eshop.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

@EventHandler
@RequiredArgsConstructor
public class OrderShippedDomainEventHandler implements DomainEventHandler<OrderShippedDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderShippedDomainEventHandler.class);

  private final OrderApplicationService orderApplicationService;
  private final IntegrationEventLogService integrationEventLogService;
  private final KafkaTopics topics;

  @EventListener
  public void handle(OrderShippedDomainEvent orderShippedDomainEvent) {
    logger.info(
        "Order with Id: {} has been successfully updated to status {}",
        orderShippedDomainEvent.order().getId(),
        OrderStatus.Shipped
    );

    var order = orderApplicationService.findOrder(orderShippedDomainEvent.order().getId());
    var buyer = orderApplicationService.findBuyerFor(order);

    var orderStatusChangedToCancelledIntegrationEvent = new OrderStatusChangedToShippedIntegrationEvent(
        order.getId().getUuid(), order.getOrderStatus().getStatus(), buyer.getBuyerName().getName());
    integrationEventLogService.saveEvent(orderStatusChangedToCancelledIntegrationEvent, topics.getShippedOrders());
  }
}
