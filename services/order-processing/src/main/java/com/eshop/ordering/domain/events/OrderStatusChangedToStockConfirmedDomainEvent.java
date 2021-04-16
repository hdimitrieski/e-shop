package com.eshop.ordering.domain.events;

import com.eshop.ordering.domain.seedwork.DomainEvent;

public record OrderStatusChangedToStockConfirmedDomainEvent(Long orderId) implements DomainEvent {
}
