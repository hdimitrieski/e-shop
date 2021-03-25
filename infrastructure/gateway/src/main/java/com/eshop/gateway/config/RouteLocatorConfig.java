package com.eshop.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
@Configuration
public class RouteLocatorConfig {
  private final TokenRelayGatewayFilterFactory filterFactory;

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("catalog", r -> r.method(HttpMethod.GET).and().path("/catalog/items")
            .filters(f -> f.filters(filterFactory.apply())
                .removeRequestHeader("Cookie")) // Prevents cookie being sent downstream
            .uri("http://localhost:8080")) // Taking advantage of docker naming
        .route("basket", r -> r
            .method(HttpMethod.GET, HttpMethod.DELETE).and().path("/basket/{customerId}")
            .or()
            .method(HttpMethod.POST).and().path("/basket/checkout")
            .filters(f -> f.filters(filterFactory.apply()).removeRequestHeader("Cookie"))
            .uri("http://localhost:8081"))
        .build();
  }

}
