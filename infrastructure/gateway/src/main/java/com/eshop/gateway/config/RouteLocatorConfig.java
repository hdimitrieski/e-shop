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

  private static final String COOKIE_HEADER_NAME = "Cookie";

  private static final String CATALOG_PATH = "/api/v1/catalog/**";
  private static final String API_PATH = "api/v1";
  private static final String BASKET_PATH = "/api/v1/basket/*";
  private static final String[] ORDER_PATH = new String[]{"/api/v1/orders", "/api/v1/orders/*"};
  private static final String[] RATING_PATH = new String[]{"/api/v1/rating", "/api/v1/rating/*"};

  private static final String CATALOG_QUERY_ROUTE_ID = "catalog-query";
  private static final String CATALOG_COMMAND_ROUTE_ID = "catalog-command";
  private static final String BASKET_ROUTE_ID = "basket";
  private static final String ORDERS_ROUTE_ID = "orders";
  private static final String RATING_ROUTE_ID = "rating";

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
      .route(CATALOG_QUERY_ROUTE_ID, r -> r
        .method(HttpMethod.GET)
        .and()
        .path(CATALOG_PATH)
        .filters(f -> f
          .removeRequestHeader(COOKIE_HEADER_NAME) // Prevents cookie being sent downstream
          .circuitBreaker(config -> config.setName(CATALOG_CIRCUIT_BREAKER))
          .rewritePath(API_PATH, ""))
        .uri(loadBalancerUriFor(eshopServices.getCatalogQuery()))
      )
      .route(CATALOG_COMMAND_ROUTE_ID, r -> r
        .method(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
        .and()
        .path(CATALOG_PATH)
        .filters(f -> f
          .removeRequestHeader(COOKIE_HEADER_NAME) // Prevents cookie being sent downstream
          .rewritePath(API_PATH, ""))
        .uri(loadBalancerUriFor(eshopServices.getCatalogCommand()))
      )
      .route(BASKET_ROUTE_ID, r -> r
        .order(0)
        .path(BASKET_PATH)
        .filters(f -> f
          .removeRequestHeader(COOKIE_HEADER_NAME)
          .circuitBreaker(config -> config.setName(BASKET_CIRCUIT_BREAKER))
          .rewritePath(API_PATH, "")
        )
        .uri(loadBalancerUriFor(eshopServices.getBasket()))
      )
      .route(ORDERS_ROUTE_ID, r -> r
        .path(ORDER_PATH)
        .filters(f -> f
          .removeRequestHeader(COOKIE_HEADER_NAME)
          .circuitBreaker(config -> config.setName(ORDER_CIRCUIT_BREAKER))
          .rewritePath(API_PATH, "")
        )
        .uri(loadBalancerUriFor(eshopServices.getOrderProcessing()))
      )
      .route(RATING_ROUTE_ID, r -> r
        .order(0)
        .method(HttpMethod.GET, HttpMethod.POST)
        .and()
        .path(RATING_PATH)
        .filters(f -> f
          .removeRequestHeader(COOKIE_HEADER_NAME)
          .circuitBreaker(config -> config.setName(RATING_CIRCUIT_BREAKER))
          .rewritePath(API_PATH, "")
        )
        .uri(loadBalancerUriFor(eshopServices.getRating()))
      )
      .build();
  }

  private String loadBalancerUriFor(String serviceName) {
    return "lb://%s".formatted(serviceName);
  }

}
