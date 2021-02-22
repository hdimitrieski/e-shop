package com.eshop.ordering.api.application.domaineventhandlers.orderstartedevent;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.api.application.integrationevents.OrderingIntegrationEventService;
import com.eshop.ordering.api.application.integrationevents.events.OrderStatusChangedToSubmittedIntegrationEvent;
import com.eshop.ordering.domain.aggregatesmodel.buyer.Buyer;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.events.OrderStartedDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ValidateOrAddBuyerAggregateWhenOrderStartedDomainEventHandler implements DomainEventHandler<OrderStartedDomainEvent> {
  private final OrderingIntegrationEventService orderingIntegrationEventService;
  private final BuyerRepository buyerRepository;

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

//    var buyerUpdated = buyerOriginallyExisted ?
//        buyerRepository.update(buyer) :
//        buyerRepository.add(buyer);
    var savedBuyer = buyerRepository.save(buyer);

//    buyerRepository.unitOfWork().saveEntities();

    var orderStatusChangedToSubmittedIntegrationEvent = new OrderStatusChangedToSubmittedIntegrationEvent(
        orderStartedEvent.order().getId(), orderStartedEvent.order().getOrderStatus().getName(), buyer.getName());
    orderingIntegrationEventService.addAndSaveEvent(orderStatusChangedToSubmittedIntegrationEvent);

    System.out.printf(
        "Buyer {%d} and related payment method were validated or updated for orderId: {%d}.%n",
        savedBuyer.getId(), orderStartedEvent.order().getId()
    );
  }
}
