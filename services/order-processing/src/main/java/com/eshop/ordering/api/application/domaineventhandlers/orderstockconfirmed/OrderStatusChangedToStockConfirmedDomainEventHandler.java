package com.eshop.ordering.api.application.domaineventhandlers.orderstockconfirmed;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToStockConfirmedIntegrationEvent;
import com.eshop.ordering.api.application.services.OrderApplicationService;
import com.eshop.ordering.config.KafkaTopics;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderStatus;
import com.eshop.ordering.domain.events.OrderStatusChangedToStockConfirmedDomainEvent;
import com.eshop.ordering.shared.EventHandler;
import com.eshop.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

@EventHandler
@RequiredArgsConstructor
public class OrderStatusChangedToStockConfirmedDomainEventHandler implements DomainEventHandler<OrderStatusChangedToStockConfirmedDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToStockConfirmedDomainEventHandler.class);

  private final OrderApplicationService orderApplicationService;
  private final IntegrationEventLogService integrationEventLogService;
  private final KafkaTopics topics;

  @EventListener
  public void handle(OrderStatusChangedToStockConfirmedDomainEvent event) {
    logger.info(
        "Order with Id: {} has been successfully updated to status {}",
        event.orderId(),
        OrderStatus.StockConfirmed
    );

    var order = orderApplicationService.findOrder(event.orderId());
    var buyer = orderApplicationService.findBuyerFor(order);

    var orderStatusChangedToStockConfirmedIntegrationEvent = new OrderStatusChangedToStockConfirmedIntegrationEvent(
        order.getId().getUuid(), order.getOrderStatus().getStatus(), buyer.getBuyerName().getName());
    integrationEventLogService.saveEvent(orderStatusChangedToStockConfirmedIntegrationEvent, topics.getStockConfirmed());
  }
}
