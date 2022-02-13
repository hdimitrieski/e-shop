package com.eshop.gqlgateway;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.netflix.graphql.dgs.apq.AutomatedPersistedQueryCaffeineCache;
import graphql.execution.preparsed.PreparsedDocumentEntry;
import graphql.execution.preparsed.persisted.PersistedQueryCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
@EnableEurekaClient
public class GqlGatewayApplication {
  public static void main(String[] args) {
    SpringApplication.run(GqlGatewayApplication.class, args);
  }

  @Bean
  public PersistedQueryCache persistedQueryCache(Cache<String, PreparsedDocumentEntry> cache) {
    return new AutomatedPersistedQueryCaffeineCache(cache);
  }

  @Bean
  public Cache<String, PreparsedDocumentEntry> apqCaffeineCache() {
    return Caffeine.newBuilder()
      .maximumSize(1000)
      .expireAfterAccess(Duration.ofHours(1))
      .build();
  }

}
