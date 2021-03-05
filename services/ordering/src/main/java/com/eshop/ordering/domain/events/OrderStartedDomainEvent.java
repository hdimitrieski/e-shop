package com.eshop.ordering.domain.events;

import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.seedwork.DomainEvent;

import java.time.LocalDate;

/**
 * Event used when an order is created.
 */
public record OrderStartedDomainEvent(
    Order order,
    String userId,
    String userName,
    Integer cardTypeId,
    String cardNumber,
    String cardSecurityNumber,
    String cardHolderName,
    LocalDate cardExpiration
) implements DomainEvent {
}
