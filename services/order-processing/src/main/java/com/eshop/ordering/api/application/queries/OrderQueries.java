package com.eshop.ordering.api.application.queries;

import java.util.List;

public interface OrderQueries {
  OrderViewModel.Order getOrder(Long id);

  List<OrderViewModel.OrderSummary> getOrdersFromUser(String userId);

  List<OrderViewModel.CardType> getCardTypes();
}
