package com.eshop.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerFactoryConfig {

  static final String CATALOG_CIRCUIT_BREAKER = "catalog-query";
  static final String BASKET_CIRCUIT_BREAKER = "basket";
  static final String ORDER_CIRCUIT_BREAKER = "order";
  static final String RATING_CIRCUIT_BREAKER = "rating";

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> catalogCustomizer(
      CircuitBreakerRegistry circuitBreakerRegistry,
      TimeLimiterRegistry timeLimiterRegistry
  ) {
    return factory -> factory.configure(builder -> builder
            .circuitBreakerConfig(circuitBreakerConfig(CATALOG_CIRCUIT_BREAKER, circuitBreakerRegistry))
            .timeLimiterConfig(timeLimiterConfig(CATALOG_CIRCUIT_BREAKER, timeLimiterRegistry)).build(),
        CATALOG_CIRCUIT_BREAKER);

  }

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> basketCustomizer(CircuitBreakerRegistry circuitBreakerRegistry) {
    return factory -> factory.configure(builder -> builder
            .circuitBreakerConfig(circuitBreakerConfig(BASKET_CIRCUIT_BREAKER, circuitBreakerRegistry))
            .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build()).build(),
        BASKET_CIRCUIT_BREAKER);
  }

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> orderCustomizer(
      CircuitBreakerRegistry circuitBreakerRegistry,
      TimeLimiterRegistry timeLimiterRegistry
  ) {
    return factory -> factory.configure(builder -> builder
            .circuitBreakerConfig(circuitBreakerConfig(ORDER_CIRCUIT_BREAKER, circuitBreakerRegistry))
            .timeLimiterConfig(timeLimiterConfig(ORDER_CIRCUIT_BREAKER, timeLimiterRegistry)).build(),
        ORDER_CIRCUIT_BREAKER);
  }

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> ratingCustomizer(
      CircuitBreakerRegistry circuitBreakerRegistry,
      TimeLimiterRegistry timeLimiterRegistry
  ) {
    return factory -> factory.configure(builder -> builder
            .circuitBreakerConfig(circuitBreakerConfig(RATING_CIRCUIT_BREAKER, circuitBreakerRegistry))
            .timeLimiterConfig(timeLimiterConfig(RATING_CIRCUIT_BREAKER, timeLimiterRegistry)).build(),
        RATING_CIRCUIT_BREAKER);
  }

  private CircuitBreakerConfig circuitBreakerConfig(String name, CircuitBreakerRegistry circuitBreakerRegistry) {
    return circuitBreakerRegistry
        .find(name)
        .orElse(CircuitBreaker.ofDefaults(name))
        .getCircuitBreakerConfig();
  }

  private TimeLimiterConfig timeLimiterConfig(String name, TimeLimiterRegistry timeLimiterRegistry) {
    return timeLimiterRegistry
        .find(name)
        .orElse(TimeLimiter.ofDefaults())
        .getTimeLimiterConfig();
  }

}
