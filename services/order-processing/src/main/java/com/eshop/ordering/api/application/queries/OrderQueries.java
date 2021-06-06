package com.eshop.ordering.api.application.queries;

import java.util.List;
import java.util.Optional;

public interface OrderQueries {
  Optional<OrderViewModel.Order> getOrder(Long id);

  List<OrderViewModel.OrderSummary> getOrdersFromUser(String userId);

  List<OrderViewModel.CardType> getCardTypes();
}
