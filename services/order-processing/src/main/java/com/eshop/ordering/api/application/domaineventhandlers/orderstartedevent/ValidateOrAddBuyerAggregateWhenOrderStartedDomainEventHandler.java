package com.eshop.ordering.api.application.domaineventhandlers.orderstartedevent;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToSubmittedIntegrationEvent;
import com.eshop.ordering.config.KafkaTopics;
import com.eshop.ordering.domain.aggregatesmodel.buyer.Buyer;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.aggregatesmodel.buyer.CardType;
import com.eshop.ordering.domain.aggregatesmodel.buyer.PaymentMethodData;
import com.eshop.ordering.domain.events.OrderStartedDomainEvent;
import com.eshop.ordering.shared.EventHandler;
import com.eshop.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@EventHandler
@RequiredArgsConstructor
public class ValidateOrAddBuyerAggregateWhenOrderStartedDomainEventHandler implements DomainEventHandler<OrderStartedDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(ValidateOrAddBuyerAggregateWhenOrderStartedDomainEventHandler.class);

  private final IntegrationEventLogService integrationEventLogService;
  private final BuyerRepository buyerRepository;
  private final KafkaTopics topics;

  @EventListener
  public void handle(OrderStartedDomainEvent orderStartedEvent) {
    final var buyer = verifyOrAddPaymentMethod(orderStartedEvent);
    final var order = orderStartedEvent.order();
    final var orderItems = order.getOrderItems().stream()
        .map(orderItem -> new OrderStatusChangedToSubmittedIntegrationEvent.OrderItemDto(
            order.getId().getUuid(),
            orderItem.orderItemProductName(),
            orderItem.getUnitPrice().getValue(),
            orderItem.getUnits().getValue()
        )).collect(Collectors.toList());
    final var orderStatusChangedToSubmittedIntegrationEvent = new OrderStatusChangedToSubmittedIntegrationEvent(
        order.getId().getUuid(),
        order.getOrderStatus().getStatus(),
        buyer.getBuyerName().getName(),
        order.getTotal().getValue(),
        orderItems
    );

    integrationEventLogService.saveEvent(orderStatusChangedToSubmittedIntegrationEvent, topics.getSubmittedOrders());

    logger.info(
        "Buyer {} and related payment method were validated or updated for orderId: {}.",
        buyer.getId(),
        order.getId()
    );
  }

  private Buyer verifyOrAddPaymentMethod(OrderStartedDomainEvent orderStartedEvent) {
    final var buyer = buyerRepository.findByUserId(orderStartedEvent.userId())
        .orElseGet(() -> new Buyer(orderStartedEvent.userId(), orderStartedEvent.buyerName()));
    buyer.verifyOrAddPaymentMethod(paymentMethodDataFor(orderStartedEvent), orderStartedEvent.order().getId());
    return buyerRepository.save(buyer);
  }

  private PaymentMethodData paymentMethodDataFor(OrderStartedDomainEvent orderStartedEvent) {
    final var cardType = nonNull(orderStartedEvent.cardType()) ? orderStartedEvent.cardType() : CardType.Amex;
    return PaymentMethodData.builder()
        .cardType(cardType)
        .alias("Payment Method on {%s}".formatted(LocalDateTime.now()))
        .cardNumber(orderStartedEvent.cardNumber())
        .expiration(orderStartedEvent.cardExpiration())
        .securityNumber(orderStartedEvent.cardSecurityNumber())
        .cardHolderName(orderStartedEvent.cardHolderName())
        .build();
  }
}
