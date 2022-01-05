package com.eshop.gqlgateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@LoadBalancerClient(value = "catalog-query", configuration = RoundRobinLoadBalancerConfig.class)
public class CatalogRestTemplateConfig {

  @LoadBalanced
  @Bean
  RestTemplate catalogRestTemplate() {
    return new RestTemplate();
  }

}
