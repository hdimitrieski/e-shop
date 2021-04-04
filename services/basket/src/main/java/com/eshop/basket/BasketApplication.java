package com.eshop.basket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.eshop")
@EnableEurekaClient
public class BasketApplication {
  public static void main(String[] args) {
    SpringApplication.run(BasketApplication.class, args);
  }
}
