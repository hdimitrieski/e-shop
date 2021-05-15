package com.eshop.ordering.api.application.domaineventhandlers.ordercancelled;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToCancelledIntegrationEvent;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderStatus;
import com.eshop.ordering.domain.events.OrderCancelledDomainEvent;
import com.eshop.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCancelledDomainEventHandler implements DomainEventHandler<OrderCancelledDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderCancelledDomainEventHandler.class);

  private final OrderRepository orderRepository;
  private final BuyerRepository buyerRepository;
  private final IntegrationEventLogService integrationEventLogService;
  @Value("${spring.kafka.consumer.topic.cancelledOrders}")
  private String cancelledOrdersTopic;

  @EventListener
  public void handle(OrderCancelledDomainEvent orderCancelledDomainEvent) {
    logger.info(
        "Order with Id: {} has been successfully updated to status {} ({})",
        orderCancelledDomainEvent.order().getId(),
        OrderStatus.Cancelled,
        OrderStatus.Cancelled.getId()
    );

    var order = orderRepository.findById(orderCancelledDomainEvent.order().getId()).orElse(null);
    var buyer = buyerRepository.findById(order.getBuyerId()).orElse(null);

    var orderStatusChangedToCancelledIntegrationEvent = new OrderStatusChangedToCancelledIntegrationEvent(
        order.getId(), order.getOrderStatus().getName(), buyer.getName());
    integrationEventLogService.saveEvent(orderStatusChangedToCancelledIntegrationEvent, cancelledOrdersTopic);
  }

}
