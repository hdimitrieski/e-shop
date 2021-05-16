package com.eshop.ordering;

import com.eshop.shared.outbox.EnableOutbox;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.eshop")
@EnableEurekaClient
@EnableSpringConfigured
@EnableOutbox
@EnableJpaRepositories(basePackages = {
    "com.eshop.ordering.infrastructure.repositories",
    "com.eshop.ordering.infrastructure.idempotency"
})
@EntityScan(basePackages = {
    "com.eshop.ordering.domain.aggregatesmodel.buyer",
    "com.eshop.ordering.domain.aggregatesmodel.order",
    "com.eshop.ordering.infrastructure.idempotency"
})
public class OrderingApplication {
  public static void main(String[] args) {
    SpringApplication.run(OrderingApplication.class, args);
  }
}
