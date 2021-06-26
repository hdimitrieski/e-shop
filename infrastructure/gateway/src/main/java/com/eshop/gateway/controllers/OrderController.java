package com.eshop.gateway.controllers;

import com.eshop.gateway.models.OrderData;
import com.eshop.gateway.services.BasketApiService;
import com.eshop.gateway.services.OrderingApiService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
  private final BasketApiService basketApiService;
  private final OrderingApiService orderingApiService;

  @GetMapping("draft/{basketId}")
  public Mono<OrderData> getOrderDraft(@PathVariable String basketId) {
    if (StringUtils.isEmpty(basketId)) {
      throw new IllegalArgumentException("The basketId is not valid"); // TODO bad request
    }

    return basketApiService.getById(basketId)
        .switchIfEmpty(Mono.error(new IllegalArgumentException("No basket found for id " + basketId)))
        .flatMap(orderingApiService::getOrderDraft);
  }

}
