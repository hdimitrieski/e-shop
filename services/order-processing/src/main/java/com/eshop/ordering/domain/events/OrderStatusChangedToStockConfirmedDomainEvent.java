package com.eshop.ordering.domain.events;

import com.eshop.ordering.domain.aggregatesmodel.order.OrderId;
import com.eshop.ordering.domain.base.DomainEvent;

public record OrderStatusChangedToStockConfirmedDomainEvent(OrderId orderId) implements DomainEvent {
}
