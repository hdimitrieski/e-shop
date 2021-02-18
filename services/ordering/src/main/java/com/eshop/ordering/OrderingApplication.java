package com.eshop.ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@SpringBootApplication
@EnableSpringConfigured
public class OrderingApplication {
  public static void main(String[] args) {
    SpringApplication.run(OrderingApplication.class, args);
  }
}
