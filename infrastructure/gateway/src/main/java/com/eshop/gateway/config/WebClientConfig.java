package com.eshop.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServerBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @LoadBalanced
  @Bean
  public WebClient.Builder webClient() {
    return WebClient.builder()
        .filter(new ServerBearerExchangeFilterFunction());
  }

  @LoadBalanced
  @Bean
  WebClient.Builder unauthorizedWebClient() {
    return WebClient.builder();
  }
}
