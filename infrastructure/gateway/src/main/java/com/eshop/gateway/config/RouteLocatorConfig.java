package com.eshop.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import static com.eshop.gateway.config.CircuitBreakerFactoryConfig.*;

@RequiredArgsConstructor
@Configuration
public class RouteLocatorConfig {

  private final EshopServices eshopServices;

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

    return builder.routes()
        .route("catalog-query", r -> r
            .method(HttpMethod.GET)
            .and()
            .path("/api/v1/catalog/**")
            .filters(f -> f
                .removeRequestHeader("Cookie") // Prevents cookie being sent downstream
                .circuitBreaker(config -> config.setName(CATALOG_CIRCUIT_BREAKER))
                .rewritePath("api/v1", ""))
            .uri(loadBalancerUriFor(eshopServices.getCatalogQuery()))
        )
        .route("catalog-command", r -> r
            .method(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
            .and()
            .path("/api/v1/catalog/**")
            .filters(f -> f
                .removeRequestHeader("Cookie") // Prevents cookie being sent downstream
                .rewritePath("api/v1", ""))
            .uri(loadBalancerUriFor(eshopServices.getCatalogCommand()))
        )
        .route("basket", r -> r
            .order(0)
            .path("/api/v1/basket/*")
            .filters(f -> f
                .removeRequestHeader("Cookie")
                .circuitBreaker(config -> config.setName(BASKET_CIRCUIT_BREAKER))
                .rewritePath("api/v1", "")
            )
            .uri(loadBalancerUriFor(eshopServices.getBasket()))
        )
        .route("orders", r -> r
            .path("/api/v1/orders", "/api/v1/orders/*")
            .filters(f -> f
                .removeRequestHeader("Cookie")
                .circuitBreaker(config -> config.setName(ORDER_CIRCUIT_BREAKER))
                .rewritePath("api/v1", "")
            )
            .uri(loadBalancerUriFor(eshopServices.getOrderProcessing()))
        )
        .route("rating", r -> r
            .path("/api/v1/rating", "/api/v1/rating/*")
            .filters(f -> f
                .removeRequestHeader("Cookie")
                .circuitBreaker(config -> config.setName(RATING_CIRCUIT_BREAKER))
                .rewritePath("api/v1", "")
            )
            .uri(loadBalancerUriFor(eshopServices.getRating()))
        )
        .build();
  }

  private String loadBalancerUriFor(String serviceName) {
    return "lb://%s".formatted(serviceName);
  }

}
