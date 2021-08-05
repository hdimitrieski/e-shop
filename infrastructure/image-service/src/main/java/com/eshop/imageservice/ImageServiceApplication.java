package com.eshop.imageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ImageServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(ImageServiceApplication.class, args);
  }
}
