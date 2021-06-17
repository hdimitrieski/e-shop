package com.eshop.ordering.domain.events;

import com.eshop.ordering.domain.aggregatesmodel.buyer.Buyer;
import com.eshop.ordering.domain.aggregatesmodel.buyer.PaymentMethod;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderId;
import com.eshop.ordering.domain.base.DomainEvent;

public record BuyerAndPaymentMethodVerifiedDomainEvent(
    Buyer buyer,
    PaymentMethod payment,
    OrderId orderId) implements DomainEvent {
}
