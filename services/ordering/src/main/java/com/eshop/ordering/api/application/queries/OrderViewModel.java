package com.eshop.ordering.api.application.queries;

import java.time.LocalDateTime;
import java.util.List;

public final class OrderViewModel {
  private OrderViewModel() {}

  public record OrderItem(
      String productName,
      Integer units,
      Double unitPrice,
      String pictureUrl
  ) {
  }

  public record Order(
      Long orderNumber,
      LocalDateTime date,
      Integer status,
      String description,
      String street,
      String city,
      String state,
      String zipCode,
      String country,
      List<OrderItem> orderItems,
      Double total
  ) {
  }

  public record OrderSummary(
      Long orderNumber,
      LocalDateTime date,
      Integer status, // TODO HD String
      Double total
  ) {
  }

  public record CardType(Integer id, String name) {
  }
}
