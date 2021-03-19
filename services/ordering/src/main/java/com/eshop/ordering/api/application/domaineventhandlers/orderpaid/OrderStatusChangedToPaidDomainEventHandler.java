package com.eshop.ordering.api.application.domaineventhandlers.orderpaid;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.OrderingIntegrationEventService;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToPaidIntegrationEvent;
import com.eshop.ordering.api.application.integrationevents.events.models.OrderStockItem;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderStatus;
import com.eshop.ordering.domain.events.OrderStatusChangedToPaidDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderStatusChangedToPaidDomainEventHandler implements DomainEventHandler<OrderStatusChangedToPaidDomainEvent> {
  private final OrderRepository orderRepository;
  private final BuyerRepository buyerRepository;
  private final OrderingIntegrationEventService orderingIntegrationEventService;
  @Value("${spring.kafka.consumer.topic.paidOrders}")
  private String paidOrdersTopic;

  @EventListener
  public void handle(OrderStatusChangedToPaidDomainEvent event) {
    System.out.printf(
        "Order with Id: {%d} has been successfully updated to status {%s} ({%d})%n",
        event.orderId(),
        OrderStatus.Paid,
        OrderStatus.Paid.getId()
    );

    var order = orderRepository.findById(event.orderId()).orElse(null);
    var buyer = buyerRepository.findById(order.getBuyerId()).orElse(null);

    var orderStockList = event.orderItems().stream()
        .map(orderItem -> new OrderStockItem(orderItem.getProductId(), orderItem.getUnits()))
        .collect(Collectors.toList());

    var orderStatusChangedToPaidIntegrationEvent = new OrderStatusChangedToPaidIntegrationEvent(
        event.orderId(),
        order.getOrderStatus().getName(),
        buyer.getName(),
        orderStockList);

    orderingIntegrationEventService.addAndSaveEvent(paidOrdersTopic, orderStatusChangedToPaidIntegrationEvent);
  }
}
