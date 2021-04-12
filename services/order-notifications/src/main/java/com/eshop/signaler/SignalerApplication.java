package com.eshop.signaler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.eshop")
@EnableEurekaClient
public class SignalerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SignalerApplication.class, args);
  }

}
