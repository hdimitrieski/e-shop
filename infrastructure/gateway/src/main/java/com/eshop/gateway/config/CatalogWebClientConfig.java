package com.eshop.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClient(value = "catalog-query", configuration = RoundRobinLoadBalancerConfig.class)
public class CatalogWebClientConfig {

  @LoadBalanced
  @Bean
  WebClient.Builder catalogWebClient() {
    return WebClient.builder();
  }

}
