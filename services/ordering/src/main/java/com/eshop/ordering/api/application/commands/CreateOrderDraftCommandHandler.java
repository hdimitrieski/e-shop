package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.api.application.dtos.OrderDraftDTO;
import com.eshop.ordering.api.application.models.BasketItem;
import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateOrderDraftCommandHandler implements Command.Handler<CreateOrderDraftCommand, OrderDraftDTO> {
  @Transactional
  @Override
  public OrderDraftDTO handle(CreateOrderDraftCommand message) {
    var order = Order.newDraft();
    var orderItems = message.items().stream().map(BasketItem::toOrderItemDTO).collect(Collectors.toList());

    for (var item : orderItems) {
      order.addOrderItem(item.productId(), item.productName(), item.unitPrice(), item.discount(), item.pictureUrl(), item.units());
    }

    return OrderDraftDTO.fromOrder(order);
  }
}
