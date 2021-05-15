package com.eshop.ordering.api.application.domaineventhandlers.ordershipped;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToShippedIntegrationEvent;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderStatus;
import com.eshop.ordering.domain.events.OrderShippedDomainEvent;
import com.eshop.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderShippedDomainEventHandler implements DomainEventHandler<OrderShippedDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderShippedDomainEventHandler.class);

  private final OrderRepository orderRepository;
  private final BuyerRepository buyerRepository;
  private final IntegrationEventLogService integrationEventLogService;
  @Value("${spring.kafka.consumer.topic.shippedOrders}")
  private String shippedOrdersTopic;

  @EventListener
  public void handle(OrderShippedDomainEvent orderShippedDomainEvent) {
    logger.info(
        "Order with Id: {} has been successfully updated to status {} ({})",
        orderShippedDomainEvent.order().getId(),
        OrderStatus.Shipped,
        OrderStatus.Shipped.getId()
    );

    var order = orderRepository.findById(orderShippedDomainEvent.order().getId()).orElse(null);
    var buyer = buyerRepository.findById(order.getBuyerId()).orElse(null);

    var orderStatusChangedToCancelledIntegrationEvent = new OrderStatusChangedToShippedIntegrationEvent(
        order.getId(), order.getOrderStatus().getName(), buyer.getName());
    integrationEventLogService.saveEvent(orderStatusChangedToCancelledIntegrationEvent, shippedOrdersTopic);
  }
}
