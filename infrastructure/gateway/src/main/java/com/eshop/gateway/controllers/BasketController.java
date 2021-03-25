package com.eshop.gateway.controllers;

import com.eshop.gateway.models.*;
import com.eshop.gateway.services.BasketService;
import com.eshop.gateway.services.CatalogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/basket")
public class BasketController {
  private final CatalogService catalogService;
  private final BasketService basketService;

  @PostMapping
  public Mono<BasketData> updateBasket(@RequestBody UpdateBasketRequest data) {
    if (isEmpty(data.items())) {
      throw new IllegalArgumentException("Empty basket");
    }

    var basket = StringUtils.isEmpty(data.buyerId())
        ? new BasketData(data.buyerId(), new ArrayList<>())
        : basketService.getById(data.buyerId()).onErrorReturn(new BasketData(data.buyerId(), new ArrayList<>())).block();

    var itemsCalculated = data.items().stream()
        .collect(Collectors.toMap(
            UpdateBasketRequestItemData::productId,
            b -> b,
            (prev, next) -> new UpdateBasketRequestItemData(
                prev.id(),
                prev.productId(),
                prev.quantity() + next.quantity()
            )
        )).values();

    var productIds = basket.items().stream().map(BasketDataItem::productId).collect(Collectors.toList());
    var catalogItems = catalogService.getCatalogItems(productIds).collectList().block();

    for (var item : itemsCalculated) {
      var catalogItem = catalogItems.stream().filter(ci -> ci.id().equals(item.productId()))
          .findFirst().orElse(null);
      if (catalogItem == null) {
        return Mono.error(new IllegalArgumentException("Basket contains non existing catalog item " + item.productId()));
      }
      var itemInBasket = basket.items().stream().filter(x -> x.productId().equals(item.productId()))
          .findFirst().orElse(null);

      if (itemInBasket == null) {
        basket.items().add(new BasketDataItem(
            item.id(),
            catalogItem.id(),
            catalogItem.name(),
            catalogItem.price(),
            null,
            item.quantity(),
            catalogItem.pictureUrl()
        ));
      } else {
        basket.items().remove(itemInBasket);
        basket.items().add(new BasketDataItem(
            itemInBasket.id(),
            itemInBasket.productId(),
            itemInBasket.productName(),
            itemInBasket.unitPrice(),
            itemInBasket.oldUnitPrice(),
            item.quantity(),
            itemInBasket.pictureUrl()
        ));
      }
    }

    return basketService.update(basket);
  }

  @PutMapping("items")
  public Mono<BasketData> updateQuantities(@RequestBody UpdateBasketItemRequest data) {
    if (isEmpty(data.updates())) {
      throw new IllegalArgumentException("No updates");
    }

    return basketService.getById(data.basketId())
        .flatMap(currentBasket -> {
          var updatedBasketItems = currentBasket.items().stream().map(item -> new BasketDataItem(
              item.id(),
              item.productId(),
              item.productName(),
              item.unitPrice(),
              item.oldUnitPrice(),
              data.updates().stream().filter(u -> u.basketItemId().equals(item.id()))
                  .map(UpdateBasketItemData::newQuantity)
                  .findFirst().orElse(item.quantity()),
              item.pictureUrl()
          )).collect(Collectors.toList());

          var updatedBasket = new BasketData(
              currentBasket.buyerId(),
              updatedBasketItems
          );

          return basketService.update(updatedBasket);

          // return updatedBasket;
        })
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Basket with id " + data.basketId() + " not found")));
  }

  @PostMapping("items")
  public void addBasketItem(@RequestBody AddBasketItemRequest data) {

    if (isNull(data) || isNull(data.quantity()) || data.quantity() == 0) {
      throw new IllegalArgumentException("Invalid basket item");
    }

    var item = catalogService.getCatalogItem(data.catalogItemId()).block();

    basketService.getById(data.basketId())
        .switchIfEmpty(Mono.just(new BasketData(data.basketId(), new ArrayList<>())))
        .doOnNext(currentBasket -> {
          var product = currentBasket.items().stream().filter(i -> i.productId().equals(item.id()))
              .findFirst().orElse(null);

          if (product != null) {
            currentBasket.items().remove(product);
            currentBasket.items().add(new BasketDataItem(
                product.id(),
                product.productId(),
                product.productName(),
                product.unitPrice(),
                product.oldUnitPrice(),
                product.quantity() + data.quantity(),
                product.pictureUrl()
            ));
          } else {
            currentBasket.items().add(new BasketDataItem(
                UUID.randomUUID().toString(),
                item.id(),
                item.name(),
                item.price(),
                null,
                data.quantity(),
                item.pictureUrl()
            ));
          }

          basketService.update(currentBasket);
        });

  }

}
