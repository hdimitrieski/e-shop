package com.eshop.ordering.api.application.domaineventhandlers.buyerandpaymentmethodverified;

import com.eshop.ordering.api.application.domaineventhandlers.DomainEventHandler;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import com.eshop.ordering.domain.events.BuyerAndPaymentMethodVerifiedDomainEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateOrderWhenBuyerAndPaymentMethodVerifiedDomainEventHandler
    implements DomainEventHandler<BuyerAndPaymentMethodVerifiedDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(UpdateOrderWhenBuyerAndPaymentMethodVerifiedDomainEventHandler.class);

  private final OrderRepository orderRepository;

  // Domain Logic comment:
  // When the Buyer and Buyer's payment method have been created or verified that they existed,
  // then we can update the original Order with the BuyerId and PaymentId (foreign keys)
  @EventListener
  public void handle(BuyerAndPaymentMethodVerifiedDomainEvent buyerPaymentMethodVerifiedEvent) {
    logger.info(
        "Order with Id: {} has been successfully updated with a payment method {} ({})",
        buyerPaymentMethodVerifiedEvent.orderId(),
        buyerPaymentMethodVerifiedEvent.payment().getCardType().getName(),
        buyerPaymentMethodVerifiedEvent.payment().getId()
    );

    var orderToUpdate = orderRepository.findById(buyerPaymentMethodVerifiedEvent.orderId()).orElse(null);
    orderToUpdate.setBuyerId(buyerPaymentMethodVerifiedEvent.buyer().getId());
    orderToUpdate.setPaymentMethodId(buyerPaymentMethodVerifiedEvent.payment().getId());
  }
}
