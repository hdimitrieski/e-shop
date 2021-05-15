package com.eshop.ordering.api.application.domaineventhandlers.ordergraceperiodconfirmed;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToAwaitingValidationIntegrationEvent;
import com.eshop.ordering.api.application.integrationevents.events.models.OrderStockItem;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderStatus;
import com.eshop.ordering.domain.events.OrderStatusChangedToAwaitingValidationDomainEvent;
import com.eshop.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderStatusChangedToAwaitingValidationDomainEventHandler
    implements DomainEventHandler<OrderStatusChangedToAwaitingValidationDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationDomainEventHandler.class);

  private final OrderRepository orderRepository;
  private final BuyerRepository buyerRepository;
  private final IntegrationEventLogService integrationEventLogService;
  @Value("${spring.kafka.consumer.topic.ordersWaitingForValidation}")
  private String ordersWaitingForValidationTopic;

  @EventListener
  public void handle(OrderStatusChangedToAwaitingValidationDomainEvent event) {
    logger.info(
        "Order with Id: {} has been successfully updated to status {} ({})",
        event.orderId(),
        OrderStatus.AwaitingValidation,
        OrderStatus.AwaitingValidation.getId()
    );

    var order = orderRepository.findById(event.orderId()).orElse(null);
    var buyer = buyerRepository.findById(order.getBuyerId()).orElse(null);
    var orderStockList = event.orderItems().stream()
        .map(orderItem -> new OrderStockItem(orderItem.getProductId(), orderItem.getUnits()))
        .collect(Collectors.toList());

    var orderStatusChangedToAwaitingValidationIntegrationEvent = new OrderStatusChangedToAwaitingValidationIntegrationEvent(
        order.getId(), order.getOrderStatus().getName(), buyer.getName(), orderStockList);
    integrationEventLogService.saveEvent(orderStatusChangedToAwaitingValidationIntegrationEvent, ordersWaitingForValidationTopic);
  }
}
