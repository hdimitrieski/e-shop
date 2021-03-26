package com.eshop.gateway.controllers;

import com.eshop.gateway.models.*;
import com.eshop.gateway.services.BasketService;
import com.eshop.gateway.services.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/basket")
public class BasketController {
  private final CatalogService catalogService;
  private final BasketService basketService;

  private Collection<UpdateBasketRequestItemData> calculatedItems(List<UpdateBasketRequestItemData> items) {
    return items.stream()
        .collect(Collectors.toMap(
            UpdateBasketRequestItemData::productId,
            item -> item,
            (prev, next) -> new UpdateBasketRequestItemData(
                prev.id(),
                prev.productId(),
                prev.quantity() + next.quantity()
            )
        )).values();
  }

  private List<CatalogItem> catalogItemsFor(List<BasketDataItem> basketItems) {
    var productIds = basketItems.stream().map(BasketDataItem::productId).collect(Collectors.toList());
    return catalogService.getCatalogItems(productIds).collectList().block();
  }

  private BasketDataItem updateBasketItem(BasketDataItem existingBasketItem, UpdateBasketRequestItemData requestedBasketItem) {
    return new BasketDataItem(
        existingBasketItem.id(),
        existingBasketItem.productId(),
        existingBasketItem.productName(),
        existingBasketItem.unitPrice(),
        existingBasketItem.oldUnitPrice(),
        requestedBasketItem.quantity(),
        existingBasketItem.pictureUrl()
    );
  }

  private BasketDataItem newBasketItem(UpdateBasketRequestItemData requestedBasketItem, CatalogItem catalogItem) {
    return new BasketDataItem(
        requestedBasketItem.id(),
        catalogItem.id(),
        catalogItem.name(),
        catalogItem.price(),
        null,
        requestedBasketItem.quantity(),
        catalogItem.pictureUrl()
    );
  }

  private BasketDataItem create(UpdateBasketRequestItemData requestedBasketItem, List<CatalogItem> catalogItems) {
    return findCatalogItem(catalogItems, requestedBasketItem.productId())
        .map(catalogItem -> newBasketItem(requestedBasketItem, catalogItem))
        .orElseThrow(() ->
            new IllegalArgumentException("Basket contains non existing catalog item " + requestedBasketItem.productId())
        );
  }

  private Optional<BasketDataItem> findBasketItem(List<BasketDataItem> basketItems, Long productId) {
    return basketItems.stream()
        .filter(x -> x.productId().equals(productId))
        .findFirst();
  }

  private Optional<CatalogItem> findCatalogItem(List<CatalogItem> catalogItems, Long productId) {
    return catalogItems.stream()
        .filter(catalogItem -> catalogItem.id().equals(productId))
        .findFirst();
  }

  private Mono<BasketData> updateBasket(
      BasketData basket,
      Collection<UpdateBasketRequestItemData> updatedBasketItems
  ) {
    var updatedItems = updatedBasketItems.stream()
        .map(requestedBasketItem -> {
          return findBasketItem(basket.items(), requestedBasketItem.productId())
              .map(existingBasketItem -> updateBasketItem(existingBasketItem, requestedBasketItem))
              .orElseGet(() -> create(requestedBasketItem, catalogItemsFor(basket.items())));
        }).collect(Collectors.toList());

    return basketService.update(new BasketData(basket.buyerId(), updatedItems));
  }

  @PostMapping
  public Mono<BasketData> updateBasket(@RequestBody UpdateBasketRequest data) {
    if (isEmpty(data.items())) {
      throw new IllegalArgumentException("Empty basket");
    }

    return basketService.getById(data.buyerId())
        .flatMap(basket -> updateBasket(basket, calculatedItems(data.items())));
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
