package com.eshop.gateway.services;

import com.eshop.gateway.models.CatalogItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface CatalogApiService {
  Mono<CatalogItem> getCatalogItem(UUID id);

  Flux<CatalogItem> getCatalogItems(List<UUID> ids);

  Flux<CatalogItem> getFirst5CatalogItems();
}
