package com.eshop.gqlgateway.api.converters;

import com.eshop.gqlgateway.api.NodeType;
import com.eshop.gqlgateway.models.BasketDto;
import com.eshop.gqlgateway.models.BasketItemDto;
import com.eshop.gqlgateway.types.Basket;
import com.eshop.gqlgateway.types.LineItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.eshop.gqlgateway.api.util.IdUtils.generateId;

@RequiredArgsConstructor
@Component
public class ToBasketConverter {
  private final ToBasketLineItemConverter toBasketLineItemConverter;

  public Basket convert(BasketDto basketDto) {
    return Basket.newBuilder()
      .id(generateId(NodeType.Brand, basketDto.id()))
      .lineItems(toLineItems(basketDto.items()))
      .build();
  }

  private List<LineItem> toLineItems(List<BasketItemDto> basketItems) {
    return basketItems.stream()
      .map(toBasketLineItemConverter::convert)
      .toList();
  }

}
