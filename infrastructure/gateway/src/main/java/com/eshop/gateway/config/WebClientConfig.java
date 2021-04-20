package com.eshop.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServerBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .filter(new ServerBearerExchangeFilterFunction())
        .build();
  }

  @Bean
  WebClient unauthorizedWebClient() {
    return WebClient.builder()
        .build();
  }
}
