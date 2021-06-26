package com.eshop.ordering.api.application.queries;

import java.util.List;
import java.util.Optional;

public interface OrderQueries {
  Optional<OrderViewModel.Order> getOrder(String id);

  List<OrderViewModel.OrderSummary> getOrdersFromUser(String userId);
}
