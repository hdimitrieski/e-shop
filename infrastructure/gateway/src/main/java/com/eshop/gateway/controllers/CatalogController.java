package com.eshop.gateway.controllers;

import com.eshop.gateway.models.CatalogItem;
import com.eshop.gateway.services.BestSellingProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/catalog")
public class CatalogController {
  private final BestSellingProductsService bestSellingProductsService;

  @GetMapping("topfive")
  public Flux<CatalogItem> getTopFiveCatalogItems() {
    return bestSellingProductsService.topFive();
  }
}
