package com.eshop.basket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.eshop")
public class BasketApplication {
  public static void main(String[] args) {
    SpringApplication.run(BasketApplication.class, args);
  }
}
