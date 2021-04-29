package com.eshop.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServerBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClient(value = "order", configuration = RoundRobinLoadBalancerConfig.class)
public class OrderWebClientConfig {

  @LoadBalanced
  @Bean
  public WebClient.Builder orderWebClient() {
    return WebClient.builder()
        .filter(new ServerBearerExchangeFilterFunction());
  }

}
