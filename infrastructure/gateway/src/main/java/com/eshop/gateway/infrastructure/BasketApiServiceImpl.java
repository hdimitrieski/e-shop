package com.eshop.gateway.infrastructure;

import com.eshop.gateway.models.BasketData;
import com.eshop.gateway.services.BasketApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class BasketApiServiceImpl implements BasketApiService {
  private final WebClient webClient;

  @Override
  public Mono<BasketData> getById(String id) {
    return webClient
        .get()
        .uri("http://localhost:8081/basket/" + id)
        .retrieve()
        .bodyToMono(BasketData.class)
        .onErrorReturn(new BasketData(id, new ArrayList<>()));
  }

  @Override
  public Mono<BasketData> update(BasketData currentBasket) {
    return webClient
        .post()
        .uri("http://localhost:8081/basket/")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(currentBasket)
        .retrieve()
        .bodyToMono(BasketData.class);
  }
}
