package com.eshop.ordering.api.application.queries;

import java.time.LocalDateTime;
import java.util.List;

public final class OrderViewModel {
  private OrderViewModel() {
  }

  public record OrderItem(
      String productName,
      Integer units,
      Double unitPrice,
      String pictureUrl
  ) {
  }

  public record Order(
      String orderNumber,
      LocalDateTime date,
      String status,
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
      String orderNumber,
      LocalDateTime date,
      String status,
      Double total
  ) {
  }

}
