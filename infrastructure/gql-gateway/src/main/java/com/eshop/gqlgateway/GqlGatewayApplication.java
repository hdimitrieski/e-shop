package com.eshop.gqlgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GqlGatewayApplication {
  public static void main(String[] args) {
    SpringApplication.run(GqlGatewayApplication.class, args);
  }
}
