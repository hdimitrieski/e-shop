package com.eshop.ordering.domain.events;

import com.eshop.ordering.domain.aggregatesmodel.buyer.Buyer;
import com.eshop.ordering.domain.aggregatesmodel.buyer.PaymentMethod;
import com.eshop.ordering.domain.seedwork.DomainEvent;

public record BuyerAndPaymentMethodVerifiedDomainEvent(
    Buyer buyer,
    PaymentMethod payment,
    Integer orderId) implements DomainEvent {
}
