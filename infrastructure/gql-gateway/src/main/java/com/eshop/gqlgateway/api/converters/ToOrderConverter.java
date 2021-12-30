package com.eshop.gqlgateway.api.converters;

import com.eshop.gqlgateway.api.NodeType;
import com.eshop.gqlgateway.models.OrderDto;
import com.eshop.gqlgateway.types.Order;
import com.eshop.gqlgateway.types.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static com.eshop.gqlgateway.api.util.IdUtils.generateId;

@RequiredArgsConstructor
@Component
public class ToOrderConverter {
  private final ToOrderLineItemConverter toOrderLineItemConverter;

  public Order convert(OrderDto orderDto) {
    var status = ofString(orderDto.status());
    return Order.newBuilder()
      .id(generateId(NodeType.Order, orderDto.id()))
      .date(OffsetDateTime.of(orderDto.date(), ZoneOffset.UTC))
      .paid(OrderStatus.PAID.equals(status))
      .totalPrice(orderDto.total())
      .orderNumber(orderDto.orderNumber())
      .confirmed(true)
      .status(status)
      .lineItems(orderDto.orderItems().stream().map(toOrderLineItemConverter::convert).toList())
      .build();
  }

  private OrderStatus ofString(String status) {
    return switch (status) {
      case "Shipped" -> OrderStatus.SHIPPED;
      case "AwaitingValidation" -> OrderStatus.AWAITING_VALIDATION;
      case "StockConfirmed" -> OrderStatus.STOCK_CONFIRMED;
      case "Submitted" -> OrderStatus.SUBMITTED;
      case "Cancelled" -> OrderStatus.CANCELLED;
      case "Paid" -> OrderStatus.PAID;
      default -> null;
    };
  }

}
