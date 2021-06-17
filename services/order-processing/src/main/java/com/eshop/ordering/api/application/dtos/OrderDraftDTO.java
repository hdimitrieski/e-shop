package com.eshop.ordering.api.application.dtos;

import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class OrderDraftDTO {
  private final List<OrderItemDTO> orderItems;
  private final Double total;

  public static OrderDraftDTO fromOrder(Order order) {
    return new OrderDraftDTO(
        order.getOrderItems().stream().map(oi -> new OrderItemDTO(
            oi.getProductId(),
            oi.orderItemProductName(),
            oi.getUnitPrice().getValue(),
            oi.currentDiscount().getValue(),
            oi.getUnits().getValue(),
            oi.getPictureUrl()
        )).collect(Collectors.toList()),
        order.getTotal().getValue()
    );
  }
}
