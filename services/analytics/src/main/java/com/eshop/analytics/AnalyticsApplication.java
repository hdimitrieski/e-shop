package com.eshop.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AnalyticsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AnalyticsApplication.class, args);
  }

}
