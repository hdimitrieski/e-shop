package com.eshop.gateway.controllers;

import com.eshop.gateway.models.AddBasketItemRequest;
import com.eshop.gateway.models.BasketData;
import com.eshop.gateway.models.UpdateBasketItemRequest;
import com.eshop.gateway.models.UpdateBasketRequest;
import com.eshop.gateway.services.AddBasketItemService;
import com.eshop.gateway.services.UpdateBasketService;
import com.eshop.gateway.services.UpdateBasketQuantitiesService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/basket")
public class BasketController {
  private final UpdateBasketService updateBasketService;
  private final UpdateBasketQuantitiesService updateBasketQuantitiesService;
  private final AddBasketItemService addBasketItemService;

  @PostMapping
  public Mono<BasketData> updateBasket(
      @RequestBody UpdateBasketRequest data,
      @AuthenticationPrincipal Principal principal
  ) {
    if (isEmpty(data.items())) {
      return Mono.error(new IllegalArgumentException("Empty basket"));
    }
    return updateBasketService.updateBasket(new UpdateBasketRequest(principal.getName(), data.items()));
  }

  @PutMapping("items")
  public Mono<BasketData> updateQuantities(@RequestBody UpdateBasketItemRequest data) {
    if (isEmpty(data.updates()) || StringUtils.isEmpty(data.basketId())) {
      return Mono.error(new IllegalArgumentException("Invalid update basket item request"));
    }
    return updateBasketQuantitiesService.update(data)
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Basket with id " + data.basketId() + " not found")));
  }

  @PostMapping("items")
  public Mono<BasketData> addBasketItem(@RequestBody AddBasketItemRequest data) {
    if (isNull(data) || isNull(data.quantity()) || data.quantity() == 0) {
      return Mono.error(new IllegalArgumentException("Invalid basket item"));
    }

    return addBasketItemService.addBasketItem(data);
  }
}
