package com.eshop.ordering.api.application.queries;

import java.util.List;
import java.util.UUID;

public interface OrderQueries {
  OrderViewModel.Order getOrder(Long id);

  List<OrderViewModel.OrderSummary> getOrdersFromUser(String userId); // TODO HD use UUID instead

  List<OrderViewModel.CardType> getCardTypes();
}
