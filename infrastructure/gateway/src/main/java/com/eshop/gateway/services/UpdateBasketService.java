package com.eshop.gateway.services;

import com.eshop.gateway.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UpdateBasketService {
  private final CatalogApiService catalogApiService;
  private final BasketApiService basketApiService;

  public Mono<BasketData> updateBasket(UpdateBasketRequest data) {
    return basketApiService.getById(data.buyerId())
      .zipWith(catalogItemsFor(data.items()))
      .flatMap(result -> {
        var existingBasket = result.getT1();
        var catalogItems = result.getT2();
        var updatedBasket = updateBasket(existingBasket, calculatedItems(data.items()), catalogItems);
        return basketApiService.update(updatedBasket);
      });
  }

  private BasketData updateBasket(
    BasketData basket,
    Collection<UpdateBasketRequestItemData> requestedBasketItems,
    List<CatalogItem> catalogItems
  ) {
    var updatedBasketItems = requestedBasketItems.stream()
      .map(requestedBasketItem -> findBasketItem(basket.items(), requestedBasketItem.productId())
        .map(existingBasketItem -> updateBasketItem(existingBasketItem, requestedBasketItem))
        .orElseGet(() -> create(requestedBasketItem, catalogItems)))
      .collect(Collectors.toList());
    return new BasketData(basket.id(), basket.buyerId(), updatedBasketItems);
  }

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

  private Mono<List<CatalogItem>> catalogItemsFor(List<UpdateBasketRequestItemData> basketItems) {
    var productIds = basketItems.stream().map(UpdateBasketRequestItemData::productId).collect(Collectors.toList());
    return catalogApiService.getCatalogItems(productIds).collectList();
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
      catalogItem.price(),
      requestedBasketItem.quantity(),
      catalogItem.pictureFileName()
    );
  }

  private BasketDataItem create(UpdateBasketRequestItemData requestedBasketItem, List<CatalogItem> catalogItems) {
    return findCatalogItem(catalogItems, requestedBasketItem.productId())
      .map(catalogItem -> newBasketItem(requestedBasketItem, catalogItem))
      .orElseThrow(() ->
        new IllegalArgumentException("Basket contains non existing catalog item " + requestedBasketItem.productId())
      );
  }

  private Optional<BasketDataItem> findBasketItem(List<BasketDataItem> basketItems, UUID productId) {
    return basketItems.stream()
      .filter(x -> x.productId().equals(productId))
      .findFirst();
  }

  private Optional<CatalogItem> findCatalogItem(List<CatalogItem> catalogItems, UUID productId) {
    return catalogItems.stream()
      .filter(catalogItem -> catalogItem.id().equals(productId))
      .findFirst();
  }
}
