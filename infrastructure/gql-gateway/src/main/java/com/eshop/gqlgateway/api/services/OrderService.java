package com.eshop.gqlgateway.api.services;

import com.eshop.gqlgateway.api.converters.ToOrderConverter;
import com.eshop.gqlgateway.models.OrderDto;
import com.eshop.gqlgateway.models.OrderItemDto;
import com.eshop.gqlgateway.types.LineItem;
import com.eshop.gqlgateway.types.Order;
import graphql.execution.DataFetcherResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
  private final ToOrderConverter toOrderConverter;

  public DataFetcherResult<Order> orderResultFrom(OrderDto orderDto) {
    final var productIdsByLineItemId = orderDto.orderItems().stream()
      .collect(Collectors.toMap(OrderItemDto::id, OrderItemDto::productId));

    return DataFetcherResult.<Order>newResult()
      .data(toOrderConverter.convert(orderDto))
      .localContext(productIdsByLineItemId)
      .build();
  }

  public Integer lineItemsQuantity(Order order) {
    return order.getLineItems().stream()
      .map(LineItem::getQuantity)
      .reduce(Integer::sum)
      .orElse(0);
  }

}
