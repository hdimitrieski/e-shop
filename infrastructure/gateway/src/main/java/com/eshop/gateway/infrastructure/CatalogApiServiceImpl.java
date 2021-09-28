package com.eshop.gateway.infrastructure;

import com.eshop.gateway.models.CatalogItem;
import com.eshop.gateway.services.CatalogApiService;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CircuitBreaker(name = "catalog-query")
@Retry(name = "catalog-query")
@TimeLimiter(name = "catalog-query")
@RequiredArgsConstructor
@Service
public class CatalogApiServiceImpl implements CatalogApiService {
  private final WebClient.Builder catalogWebClient;

  @Override
  public Mono<CatalogItem> getCatalogItem(UUID id) {
    return catalogWebClient.build()
        .get()
        .uri("lb://catalog-query/catalog/items/" + id)
        .retrieve()
        .bodyToMono(CatalogItem.class);
  }

  @Override
  public Flux<CatalogItem> getCatalogItems(List<UUID> ids) {
    var commaSeparatedIds = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
    return catalogWebClient.build()
        .get()
        .uri("lb://catalog-query/catalog/items/withids/" + commaSeparatedIds)
        .retrieve()
        .bodyToFlux(CatalogItem.class);
  }

  @Override
  public Flux<CatalogItem> getFirst5CatalogItems() {
    return catalogWebClient.build()
        .get()
        .uri("lb://catalog-query/catalog/items?pageIndex=0&pageSize=5")
        .retrieve()
        .bodyToMono(First5CatalogItemsResponse.class)
        .flatMapIterable(First5CatalogItemsResponse::content);
  }

  private static record First5CatalogItemsResponse(
      @JsonProperty("content") List<CatalogItem> content
  ) {
  }
}
