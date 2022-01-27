package com.eshop.gqlgateway.api.converters;

import com.eshop.gqlgateway.api.NodeType;
import com.eshop.gqlgateway.models.BasketItemDto;
import com.eshop.gqlgateway.types.LineItem;
import org.springframework.stereotype.Component;

import static com.eshop.gqlgateway.api.util.IdUtils.generateId;

@Component
public class ToBasketLineItemConverter {
  public LineItem convert(BasketItemDto basketItem) {
    return LineItem.newBuilder()
      .id(generateId(NodeType.BasketLineItem, basketItem.id()))
      .name(basketItem.productName())
      .image(basketItem.pictureUrl())
      .unitPrice(basketItem.unitPrice())
      .quantity(basketItem.quantity())
      .price(basketItem.quantity() * basketItem.unitPrice())
      .build();
  }
}
