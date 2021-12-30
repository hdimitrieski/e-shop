package com.eshop.gateway.infrastructure;

import com.eshop.gateway.models.BasketData;
import com.eshop.gateway.services.BasketApiService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasketApiServiceImpl implements BasketApiService {
  private final WebClient.Builder basketWebClient;

  @CircuitBreaker(name = "basket", fallbackMethod = "circuitBreakerEmptyBasketData")
  @Retry(name = "basket", fallbackMethod = "retryEmptyBasketData")
  @Override
  public Mono<BasketData> getById(String id) {
    return basketWebClient.build()
      .get()
      .uri("lb://basket/basket/customer/" + id)
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

  private Mono<BasketData> circuitBreakerEmptyBasketData(String id, CallNotPermittedException t) {
    return Mono.just(new BasketData(UUID.randomUUID(), id, new ArrayList<>()));
  }

  private Mono<BasketData> retryEmptyBasketData(String id, Exception t) {
    return Mono.just(new BasketData(UUID.randomUUID(), id, new ArrayList<>()));
  }

}
