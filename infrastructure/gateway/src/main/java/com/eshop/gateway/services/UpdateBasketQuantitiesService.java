package com.eshop.gateway.services;

import com.eshop.gateway.models.BasketData;
import com.eshop.gateway.models.BasketDataItem;
import com.eshop.gateway.models.UpdateBasketItemData;
import com.eshop.gateway.models.UpdateBasketItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UpdateBasketQuantitiesService {
  private final BasketApiService basketApiService;

  public Mono<BasketData> update(UpdateBasketItemRequest data) {
    return basketApiService.getById(data.basketId())
      .flatMap(basket -> basketApiService.update(updateBasketItemQuantities(basket, data.updates())));
  }

  private BasketData updateBasketItemQuantities(BasketData basket, List<UpdateBasketItemData> updates) {
    var updatedBasketItems = basket.items().stream().map(item -> new BasketDataItem(
      item.id(),
      item.productId(),
      item.productName(),
      item.unitPrice(),
      item.oldUnitPrice(),
      updatedQuantity(item, updates),
      item.pictureUrl()
    )).collect(Collectors.toList());

    return new BasketData(
      basket.id(),
      basket.buyerId(),
      updatedBasketItems
    );
  }

  private Integer updatedQuantity(BasketDataItem basketItem, List<UpdateBasketItemData> updates) {
    return updates.stream()
      .filter(u -> u.basketItemId().equals(basketItem.id()))
      .map(UpdateBasketItemData::newQuantity)
      .findFirst()
      .orElse(basketItem.quantity());
  }
}
