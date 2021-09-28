package com.eshop.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EntityScan
public class CatalogCommandApplication {
  public static void main(String[] args) {
    SpringApplication.run(CatalogCommandApplication.class, args);
  }
}
