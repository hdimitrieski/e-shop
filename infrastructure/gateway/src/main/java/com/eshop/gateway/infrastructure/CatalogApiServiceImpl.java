package com.eshop.gateway.infrastructure;

import com.eshop.gateway.models.CatalogItem;
import com.eshop.gateway.services.CatalogApiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@CircuitBreaker(name = "catalog")
@Retry(name = "catalog")
@TimeLimiter(name = "catalog")
@RequiredArgsConstructor
@Service
public class CatalogApiServiceImpl implements CatalogApiService {
  private final WebClient.Builder catalogWebClient;

  @Override
  public Mono<CatalogItem> getCatalogItem(Long id) {
    return catalogWebClient.build()
        .get()
        .uri("lb://catalog/catalog/items/" + id)
        .retrieve()
        .bodyToMono(CatalogItem.class);
  }

  @Override
  public Flux<CatalogItem> getCatalogItems(List<Long> ids) {
    var commaSeparatedIds = ids.stream().map(String::valueOf).collect(Collectors.joining(", "));
    return catalogWebClient.build()
        .get()
        .uri("lb://catalog/catalog/items/withids/" + commaSeparatedIds)
        .retrieve()
        .bodyToFlux(CatalogItem.class);
  }
}
