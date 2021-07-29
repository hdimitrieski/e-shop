package com.eshop.catalog;

import com.eshop.shared.outbox.EnableOutbox;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories
@EntityScan
@EnableOutbox
public class CatalogApplication {
  public static void main(String[] args) {
    SpringApplication.run(CatalogApplication.class, args);
  }
}
