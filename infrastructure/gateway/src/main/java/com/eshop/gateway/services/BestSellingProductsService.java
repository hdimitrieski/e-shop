package com.eshop.gateway.services;

import com.eshop.gateway.models.CatalogItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class BestSellingProductsService {
  private final AnalyticsApiService analyticsApiService;

  public Flux<CatalogItem> topFive() {
    return analyticsApiService.getTopFiveProducts();
  }

}
