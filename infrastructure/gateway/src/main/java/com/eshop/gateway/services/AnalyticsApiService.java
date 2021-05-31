package com.eshop.gateway.services;

import com.eshop.gateway.models.Product;
import reactor.core.publisher.Flux;

public interface AnalyticsApiService {
  Flux<Product> getTopFiveProducts();
}
