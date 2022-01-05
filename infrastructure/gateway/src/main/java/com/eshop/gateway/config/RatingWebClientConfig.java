package com.eshop.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClient(value = "rating", configuration = RoundRobinLoadBalancerConfig.class)
public class RatingWebClientConfig {

  @LoadBalanced
  @Bean
  WebClient.Builder ratingWebClient() {
    return WebClient.builder();
  }
}
