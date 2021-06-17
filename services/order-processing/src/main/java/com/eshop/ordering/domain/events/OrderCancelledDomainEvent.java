package com.eshop.ordering.domain.events;

import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.base.DomainEvent;

public record OrderCancelledDomainEvent(Order order) implements DomainEvent {
}
