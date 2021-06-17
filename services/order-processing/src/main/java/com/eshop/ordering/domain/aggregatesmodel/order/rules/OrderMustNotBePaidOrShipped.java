package com.eshop.ordering.domain.aggregatesmodel.order.rules;

import com.eshop.ordering.domain.aggregatesmodel.order.OrderStatus;
import com.eshop.ordering.domain.base.BusinessRule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderMustNotBePaidOrShipped implements BusinessRule {
  private final OrderStatus currentStatus;

  @Override
  public boolean broken() {
    return OrderStatus.Paid.equals(currentStatus) || OrderStatus.Shipped.equals(currentStatus);
  }

  @Override
  public String message() {
    return "It's not possible to cancel order with status {%s}.".formatted(currentStatus);
  }
}
