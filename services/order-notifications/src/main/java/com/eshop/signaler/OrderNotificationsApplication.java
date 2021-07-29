package com.eshop.signaler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrderNotificationsApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderNotificationsApplication.class, args);
  }

}
