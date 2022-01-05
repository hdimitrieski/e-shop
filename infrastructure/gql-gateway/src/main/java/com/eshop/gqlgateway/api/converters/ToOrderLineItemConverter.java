package com.eshop.gqlgateway.api.converters;

import com.eshop.gqlgateway.api.NodeType;
import com.eshop.gqlgateway.models.OrderItemDto;
import com.eshop.gqlgateway.types.LineItem;
import org.springframework.stereotype.Component;

import static com.eshop.gqlgateway.api.util.IdUtils.generateId;

@Component
public class ToOrderLineItemConverter {

  public LineItem convert(OrderItemDto orderItem) {
    return LineItem.newBuilder()
      .id(generateId(NodeType.OrderLineItem, orderItem.id()))
      .name(orderItem.productName())
      .image(orderItem.pictureUrl())
      .unitPrice(orderItem.unitPrice())
      .quantity(orderItem.units())
      .build();
  }
}
