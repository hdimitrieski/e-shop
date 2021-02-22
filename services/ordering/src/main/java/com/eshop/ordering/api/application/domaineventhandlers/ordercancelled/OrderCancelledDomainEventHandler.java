package com.eshop.ordering.api.application.domaineventhandlers.ordercancelled;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.OrderingIntegrationEventService;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToCancelledIntegrationEvent;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderStatus;
import com.eshop.ordering.domain.events.OrderCancelledDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCancelledDomainEventHandler implements DomainEventHandler<OrderCancelledDomainEvent> {
  private final OrderRepository orderRepository;
  private final BuyerRepository buyerRepository;
  private final OrderingIntegrationEventService orderingIntegrationEventService;

  @EventListener
  public void handle(OrderCancelledDomainEvent orderCancelledDomainEvent) {
    System.out.printf(
        "Order with Id: {%d} has been successfully updated to status {%s} ({%d})%n",
        orderCancelledDomainEvent.order().getId(),
        OrderStatus.Cancelled,
        OrderStatus.Cancelled.getId()
    );

    var order = orderRepository.findById(orderCancelledDomainEvent.order().getId()).orElse(null);
    var buyer = buyerRepository.findById(order.getBuyerId()).orElse(null);

    var orderStatusChangedToCancelledIntegrationEvent = new OrderStatusChangedToCancelledIntegrationEvent(
        order.getId(), order.getOrderStatus().getName(), buyer.getName());
    orderingIntegrationEventService.addAndSaveEvent(orderStatusChangedToCancelledIntegrationEvent);
  }

}
