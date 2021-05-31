package com.eshop.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClient(value = "analytics", configuration = RoundRobinLoadBalancerConfig.class)
public class AnalyticsWebClientConfig {

  @LoadBalanced
  @Bean
  public WebClient.Builder analyticsWebClient() {
    return WebClient.builder();
  }

}
