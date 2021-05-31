package com.eshop.gateway.services;

import com.eshop.gateway.models.CatalogItem;
import com.eshop.gateway.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class BestSellingProductsService {
  private final AnalyticsApiService analyticsApiService;
  private final CatalogApiService catalogApiService;

  public Flux<CatalogItem> topFive() {
    return analyticsApiService.getTopFiveProducts()
        .map(Product::id)
        .collectList()
        .flatMapMany(catalogApiService::getCatalogItems);
  }

}
