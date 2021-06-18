package com.eshop.gateway.infrastructure;

import com.eshop.gateway.models.Product;
import com.eshop.gateway.services.AnalyticsApiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@CircuitBreaker(name = "catalog")
@Retry(name = "catalog")
@RequiredArgsConstructor
@Service
public class AnalyticsApiServiceImpl implements AnalyticsApiService {
  private final WebClient.Builder analyticsWebClient;

  @Value("${app.security.oauth2.client.analytics}")
  private String analyticsClientId;

  // TODO Fallback: Get first five catalog items
  @Override
  public Flux<Product> getTopFiveProducts() {
    return analyticsWebClient.build()
        .get()
        .uri("lb://analytics/analytics/products/top-five")
        .attributes(clientRegistrationId(analyticsClientId))
        .retrieve()
        .bodyToFlux(Product.class);
  }
}
