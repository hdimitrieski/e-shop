package com.eshop.ordering.api.application.domaineventhandlers.ordershipped;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.OrderingIntegrationEventService;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToShippedIntegrationEvent;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderStatus;
import com.eshop.ordering.domain.events.OrderShippedDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderShippedDomainEventHandler implements DomainEventHandler<OrderShippedDomainEvent> {
  private final OrderRepository orderRepository;
  private final BuyerRepository buyerRepository;
  private final OrderingIntegrationEventService orderingIntegrationEventService;

  @EventListener
  public void handle(OrderShippedDomainEvent orderShippedDomainEvent) {
    System.out.printf(
        "Order with Id: {%d} has been successfully updated to status {%s} ({%d})%n",
        orderShippedDomainEvent.order().getId(),
        OrderStatus.Shipped,
        OrderStatus.Shipped.getId()
    );

    var order = orderRepository.findById(orderShippedDomainEvent.order().getId()).orElse(null);
    var buyer = buyerRepository.findById(order.getBuyerId()).orElse(null);

    var orderStatusChangedToCancelledIntegrationEvent = new OrderStatusChangedToShippedIntegrationEvent(
        order.getId(), order.getOrderStatus().getName(), buyer.getName());
    orderingIntegrationEventService.addAndSaveEvent(orderStatusChangedToCancelledIntegrationEvent);
  }
}
