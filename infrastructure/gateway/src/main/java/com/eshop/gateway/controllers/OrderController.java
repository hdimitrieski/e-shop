package com.eshop.gateway.controllers;

import com.eshop.gateway.models.OrderData;
import com.eshop.gateway.services.BasketService;
import com.eshop.gateway.services.OrderingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/order")
public class OrderController {
  private final BasketService basketService;
  private final OrderingService orderingService;

  @GetMapping("draft/{basketId}")
  public Mono<OrderData> getOrderDraft(@PathVariable String basketId) {
    if (StringUtils.isEmpty(basketId)) {
      throw new IllegalArgumentException("The basketId is not valid"); // TODO HD bad request
    }

    return  basketService.getById(basketId)
        .filter(Objects::nonNull)
        .flatMap(orderingService::getOrderDraft)
        .switchIfEmpty(Mono.error(new IllegalArgumentException("No basket found for id " + basketId)));
  }

}
