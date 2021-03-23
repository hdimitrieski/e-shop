package com.eshop.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class RouteLocatorConfig {
  private final TokenRelayGatewayFilterFactory filterFactory;

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("catalog", r -> r.path("/catalog/items")
            .filters(f -> f.filters(filterFactory.apply())
                .removeRequestHeader("Cookie")) // Prevents cookie being sent downstream
            .uri("http://localhost:8080")) // Taking advantage of docker naming
        .build();
  }

}
