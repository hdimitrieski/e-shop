package com.eshop.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Alternatively, CORS can be configured in application.yml.
 * Check: https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#cors-configuration
 */
@Configuration
public class CorsGlobalConfiguration {

  @Value("${app.client.client-address.ng}")
  private String allowedClientAddress;

  @Bean
  public CorsWebFilter corsFilter() {
    return new CorsWebFilter(corsConfigurationSource());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final var corsConfig = new CorsConfiguration();
    corsConfig.setAllowedOrigins(List.of(allowedClientAddress));
    corsConfig.setMaxAge(3600L);
    corsConfig.setAllowedHeaders(List.of("*"));
    corsConfig.setAllowedMethods(List.of(
        HttpMethod.GET.name(),
        HttpMethod.PUT.name(),
        HttpMethod.POST.name(),
        HttpMethod.DELETE.name()
    ));
    final var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/**", corsConfig);
    return source;
  }

}
