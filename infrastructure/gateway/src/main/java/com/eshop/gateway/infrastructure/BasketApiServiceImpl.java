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
  private final WebClient.Builder webClient;

//  @CircuitBreaker(name = "basket")
//  @TimeLimiter(name = "basket")
//  @Retry(name = "basket")
  @Override
  public Mono<BasketData> getById(String id) {
    return webClient.build()
        .get()
        .uri("lb://basket/basket/" + id)
        .retrieve()
        .bodyToMono(BasketData.class)
        .onErrorReturn(new BasketData(id, new ArrayList<>()));
  }

//  @CircuitBreaker(name = "basket")
//  @Retry(name = "basket")
  @Override
  public Mono<BasketData> update(BasketData currentBasket) {
    return webClient.build()
        .post()
        .uri("lb://basket/basket/")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(currentBasket)
        .retrieve()
        .bodyToMono(BasketData.class);
  }
}
