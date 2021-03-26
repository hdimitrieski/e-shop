package com.eshop.gateway.services;

import com.eshop.gateway.models.CatalogItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CatalogApiService {
  Mono<CatalogItem> getCatalogItem(Long id);

  Flux<CatalogItem> getCatalogItems(List<Long> ids);
}
