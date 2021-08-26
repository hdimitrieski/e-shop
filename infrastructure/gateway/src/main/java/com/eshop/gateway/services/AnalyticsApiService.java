package com.eshop.gateway.services;

import com.eshop.gateway.models.CatalogItem;
import reactor.core.publisher.Flux;

public interface AnalyticsApiService {
  Flux<CatalogItem> getTopFiveProducts();
}
