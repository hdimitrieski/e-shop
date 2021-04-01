package com.eshop.gateway.infrastructure;

import com.eshop.gateway.models.CatalogItem;
import com.eshop.gateway.services.CatalogApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatalogApiServiceImpl implements CatalogApiService {
  private final WebClient unauthorizedWebClient;

  @Override
  public Mono<CatalogItem> getCatalogItem(Long id) {
    return unauthorizedWebClient
        .get()
        .uri("http://localhost:8080/catalog/items/" + id)
        .retrieve()
        .bodyToMono(CatalogItem.class);
  }

  @Override
  public Flux<CatalogItem> getCatalogItems(List<Long> ids) {
    var commaSeparatedIds = ids.stream().map(String::valueOf).collect(Collectors.joining(", "));

    return unauthorizedWebClient
        .get()
        .uri("http://localhost:8080/catalog/items/withids/" + commaSeparatedIds)
        .retrieve()
        .bodyToFlux(CatalogItem.class);
  }
}
