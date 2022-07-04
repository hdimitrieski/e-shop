package com.eshop.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class HealthCheckConfig {
  private final WebClient.Builder webClient;
  private final EshopServices eshopServices;

  /**
   * Check the health status of all core services.
   */
  @Bean
  ReactiveHealthContributor reactiveHealthContributor() {
    ReactiveHealthIndicator catalogHealthIndicator = () -> servicesHealth(eshopServices.getCatalogQuery());
    ReactiveHealthIndicator basketHealthIndicator = () -> servicesHealth(eshopServices.getBasket());
    ReactiveHealthIndicator orderProcessingHealthIndicator = () -> servicesHealth(eshopServices.getOrderProcessing());
    Map<String, ReactiveHealthContributor> healthIndicators = Map.of(
        "Catalog Service", catalogHealthIndicator,
        "Order Processing Service", orderProcessingHealthIndicator,
        "Basket Service", basketHealthIndicator
    );

    return CompositeReactiveHealthContributor.fromMap(healthIndicators);
  }

  private Mono<Health> servicesHealth(String serviceName) {
    return webClient.build()
        .get().uri("lb://%s/actuator/health".formatted(serviceName))
        .retrieve().bodyToMono(String.class)
        .map(s -> new Health.Builder()
            .up()
            .build())
        .onErrorResume(ex -> Mono.just(new Health.Builder()
            .down(ex)
            .build()))
        .log();
  }

}
