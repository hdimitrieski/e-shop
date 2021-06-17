package com.eshop.ordering.domain.events;

import com.eshop.ordering.domain.aggregatesmodel.buyer.*;
import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.base.DomainEvent;

/**
 * Event used when an order is created.
 */
public record OrderStartedDomainEvent(
    Order order,
    UserId userId,
    BuyerName buyerName,
    CardType cardType,
    CardNumber cardNumber,
    SecurityNumber cardSecurityNumber,
    CardHolder cardHolderName,
    CardExpiration cardExpiration
) implements DomainEvent {
}
