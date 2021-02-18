package com.eshop.ordering.domain.events;

import com.eshop.ordering.domain.aggregatesmodel.order.OrderItem;
import com.eshop.ordering.domain.seedwork.DomainEvent;

import java.util.List;

/**
 * Event used when the order is paid.
 */
public record OrderStatusChangedToPaidDomainEvent(
    Integer orderId,
    List<OrderItem> orderItems) implements DomainEvent {
}
