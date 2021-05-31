package com.eshop.ordering.api.application.domaineventhandlers.orderstartedevent;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToSubmittedIntegrationEvent;
import com.eshop.ordering.domain.aggregatesmodel.buyer.Buyer;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderItem;
import com.eshop.ordering.domain.events.OrderStartedDomainEvent;
import com.eshop.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidateOrAddBuyerAggregateWhenOrderStartedDomainEventHandler implements DomainEventHandler<OrderStartedDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(ValidateOrAddBuyerAggregateWhenOrderStartedDomainEventHandler.class);

  private final IntegrationEventLogService integrationEventLogService;
  private final BuyerRepository buyerRepository;
  @Value("${spring.kafka.consumer.topic.submittedOrders}")
  private String submittedOrdersTopic;

  @EventListener
  public void handle(OrderStartedDomainEvent orderStartedEvent) {
    var cardTypeId = (orderStartedEvent.cardTypeId() != 0) ? orderStartedEvent.cardTypeId() : 1;
    var buyer = buyerRepository.findByIdentityGuid(orderStartedEvent.userId());
    boolean buyerOriginallyExisted = buyer != null;

    if (!buyerOriginallyExisted) {
      buyer = new Buyer(orderStartedEvent.userId(), orderStartedEvent.userName());
    }

    buyer.verifyOrAddPaymentMethod(cardTypeId,
        "Payment Method on {%s}".formatted(LocalDateTime.now()),
        orderStartedEvent.cardNumber(),
        orderStartedEvent.cardSecurityNumber(),
        orderStartedEvent.cardHolderName(),
        orderStartedEvent.cardExpiration(),
        orderStartedEvent.order().getId());

    var savedBuyer = buyerRepository.save(buyer);

    var order = orderStartedEvent.order();
    var orderItems = order.getOrderItems().stream()
        .map(orderItem -> new OrderStatusChangedToSubmittedIntegrationEvent.OrderItemDto(
            order.getId(), orderItem.getOrderItemProductName(), orderItem.getUnitPrice(), orderItem.getUnits()
        )).collect(Collectors.toList());
    var orderStatusChangedToSubmittedIntegrationEvent = new OrderStatusChangedToSubmittedIntegrationEvent(
        order.getId(),
        order.getOrderStatus().getName(),
        buyer.getName(),
        order.getTotal(),
        orderItems
    );
    integrationEventLogService.saveEvent(orderStatusChangedToSubmittedIntegrationEvent, submittedOrdersTopic);

    logger.info(
        "Buyer {} and related payment method were validated or updated for orderId: {}.",
        savedBuyer.getId(),
        order.getId()
    );
  }
}
