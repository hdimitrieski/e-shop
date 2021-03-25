package com.eshop.gateway.services;

import com.eshop.gateway.models.CatalogItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService {
  private final WebClient webClient;

  @Override
  public Mono<CatalogItem> getCatalogItem(Long id) {
    return webClient
        .get()
        .uri("http://localhost:8081/catalog/items/" + id)
        .retrieve()
        .bodyToMono(CatalogItem.class);
  }

  @Override
  public Flux<CatalogItem> getCatalogItems(List<Long> ids) {
    var commaSeparatedIds = ids.stream().map(String::valueOf).collect(Collectors.joining(", "));

    return webClient
        .get()
        .uri("http://localhost:8081/catalog/items/withids" + commaSeparatedIds)
        .retrieve()
        .bodyToFlux(CatalogItem.class);
  }
}
