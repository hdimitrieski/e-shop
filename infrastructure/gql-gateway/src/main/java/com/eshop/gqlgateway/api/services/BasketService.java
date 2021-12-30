package com.eshop.gqlgateway.api.services;

import com.eshop.gqlgateway.api.converters.ToBasketConverter;
import com.eshop.gqlgateway.models.BasketDto;
import com.eshop.gqlgateway.models.BasketItemDto;
import com.eshop.gqlgateway.types.Basket;
import com.eshop.gqlgateway.types.LineItem;
import graphql.execution.DataFetcherResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BasketService {
  private final ToBasketConverter toBasketConverter;

  public DataFetcherResult<Basket> basketResultFrom(BasketDto basketDto) {
    final var productIdsByLineItemId = basketDto.items().stream()
      .collect(Collectors.toMap(BasketItemDto::id, BasketItemDto::productId));

    return DataFetcherResult.<Basket>newResult()
      .data(toBasketConverter.convert(basketDto))
      .localContext(productIdsByLineItemId)
      .build();
  }

  public Integer lineItemsQuantity(Basket basket) {
    return basket.getLineItems().stream()
      .map(LineItem::getQuantity)
      .reduce(Integer::sum)
      .orElse(0);
  }

  public Double totalPrice(Basket basket) {
    return basket.getLineItems().stream()
      .map(LineItem::getPrice)
      .reduce(Double::sum)
      .orElse(0D);
  }

}
