package com.eshop.ordering.api.application.domaineventhandlers.ordercancelled;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToCancelledIntegrationEvent;
import com.eshop.ordering.api.application.services.OrderApplicationService;
import com.eshop.ordering.config.KafkaTopics;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderStatus;
import com.eshop.ordering.domain.events.OrderCancelledDomainEvent;
import com.eshop.ordering.shared.EventHandler;
import com.eshop.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

@EventHandler
@RequiredArgsConstructor
public class OrderCancelledDomainEventHandler implements DomainEventHandler<OrderCancelledDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderCancelledDomainEventHandler.class);

  private final OrderApplicationService orderApplicationService;
  private final IntegrationEventLogService integrationEventLogService;
  private final KafkaTopics topics;

  @EventListener
  public void handle(OrderCancelledDomainEvent orderCancelledDomainEvent) {
    logger.info(
        "Order with Id: {} has been successfully updated to status {}",
        orderCancelledDomainEvent.order().getId(),
        OrderStatus.Cancelled
    );

    var order = orderApplicationService.findOrder(orderCancelledDomainEvent.order().getId());
    var buyer = orderApplicationService.findBuyerFor(order);

    var orderStatusChangedToCancelledIntegrationEvent = new OrderStatusChangedToCancelledIntegrationEvent(
        order.getId().getUuid(), order.getOrderStatus().getStatus(), buyer.getBuyerName().getName());
    integrationEventLogService.saveEvent(orderStatusChangedToCancelledIntegrationEvent, topics.getCancelledOrders());
  }

}
