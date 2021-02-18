package com.eshop.ordering.api.application.commands;

import com.eshop.ordering.api.application.dtos.OrderDraftDTO;
import com.eshop.ordering.api.application.models.BasketItem;
import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreateOrderDraftCommandHandler {
  private final OrderRepository orderRepository;

  public OrderDraftDTO handle(CreateOrderDraftCommand message) {
    var order = Order.newDraft();
    var orderItems = message.getItems().stream().map(BasketItem::toOrderItemDTO).collect(Collectors.toList());

    for (var item : orderItems) {
      order.addOrderItem(item.productId(), item.productName(), item.unitPrice(), item.discount(), item.pictureUrl(), item.units());
    }

    return OrderDraftDTO.fromOrder(order);
  }
}
