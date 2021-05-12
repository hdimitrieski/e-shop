package com.eshop.gateway.services;

import com.eshop.gateway.models.AddBasketItemRequest;
import com.eshop.gateway.models.BasketData;
import com.eshop.gateway.models.BasketDataItem;
import com.eshop.gateway.models.CatalogItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AddBasketItemService {
  private final CatalogApiService catalogApiService;
  private final BasketApiService basketApiService;

  public Mono<BasketData> addBasketItem(AddBasketItemRequest data) {

    return basketApiService.getById(data.basketId())
        .zipWith(
            catalogApiService.getCatalogItem(data.catalogItemId())
                .onErrorResume(throwable -> Mono.empty())
        )
        .flatMap(result -> {
          var basket = result.getT1();
          var catalogItem = result.getT2();
          var updatedBasket = addBasketItem(basket, data, catalogItem);

          return basketApiService.update(updatedBasket);
        });
  }

  private BasketData addBasketItem(BasketData basket, AddBasketItemRequest data, CatalogItem catalogItem) {
    var existingProduct = basket.items().stream()
        .filter(i -> i.productId().equals(catalogItem.id()))
        .findFirst()
        .orElse(null);

    if (existingProduct != null) {
      basket.items().remove(existingProduct);
      basket.items().add(new BasketDataItem(
          existingProduct.id(),
          existingProduct.productId(),
          existingProduct.productName(),
          existingProduct.unitPrice(),
          existingProduct.oldUnitPrice(),
          existingProduct.quantity() + data.quantity(),
          existingProduct.pictureUrl()
      ));
    } else {
      basket.items().add(new BasketDataItem(
          UUID.randomUUID().toString(),
          catalogItem.id(),
          catalogItem.name(),
          catalogItem.price(),
          catalogItem.price(),
          data.quantity(),
          catalogItem.pictureFileName()
      ));
    }

    return basket;
  }

}
