package com.eshop.gqlgateway.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@LoadBalancerClient(value = "basket", configuration = RoundRobinLoadBalancerConfig.class)
public class BasketRestTemplateConfig {

  @LoadBalanced
  @Bean
  public RestTemplate basketRestTemplate() {
    return new RestTemplateBuilder()
      .additionalInterceptors(new BearerExchangeInterceptor())
      .build();
  }

}
