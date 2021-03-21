package com.eshop.ordering.api.application.domaineventhandlers.orderstartedevent;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.OrderingIntegrationEventService;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToSubmittedIntegrationEvent;
import com.eshop.ordering.domain.aggregatesmodel.buyer.Buyer;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.events.OrderStartedDomainEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ValidateOrAddBuyerAggregateWhenOrderStartedDomainEventHandler implements DomainEventHandler<OrderStartedDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(ValidateOrAddBuyerAggregateWhenOrderStartedDomainEventHandler.class);

  private final OrderingIntegrationEventService orderingIntegrationEventService;
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

    var orderStatusChangedToSubmittedIntegrationEvent = new OrderStatusChangedToSubmittedIntegrationEvent(
        orderStartedEvent.order().getId(), orderStartedEvent.order().getOrderStatus().getName(), buyer.getName());
    orderingIntegrationEventService.addAndSaveEvent(submittedOrdersTopic, orderStatusChangedToSubmittedIntegrationEvent);

    logger.info(
        "Buyer {} and related payment method were validated or updated for orderId: {}.",
        savedBuyer.getId(),
        orderStartedEvent.order().getId()
    );
  }
}
