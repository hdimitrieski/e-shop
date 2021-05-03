package com.eshop.gateway.infrastructure;

import com.eshop.gateway.models.BasketData;
import com.eshop.gateway.services.BasketApiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class BasketApiServiceImpl implements BasketApiService {
  private final WebClient.Builder basketWebClient;

  @CircuitBreaker(name = "basket", fallbackMethod = "emptyBasketData")
  @Retry(name = "basket", fallbackMethod = "emptyBasketData")
  @Override
  public Mono<BasketData> getById(String id) {
    return basketWebClient.build()
        .get()
        .uri("lb://basket/basket/" + id)
        .retrieve()
        .bodyToMono(BasketData.class);
  }

  @CircuitBreaker(name = "basket")
  @Retry(name = "basket")
  @Override
  public Mono<BasketData> update(BasketData currentBasket) {
    return basketWebClient.build()
        .post()
        .uri("lb://basket/basket/")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(currentBasket)
        .retrieve()
        .bodyToMono(BasketData.class);
  }

  private Mono<BasketData> emptyBasketData(String id, Exception t) {
    return Mono.just(new BasketData(id, new ArrayList<>()));
  }

}
