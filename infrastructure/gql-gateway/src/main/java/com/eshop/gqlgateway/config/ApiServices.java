package com.eshop.gqlgateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.services")
@Data
@RefreshScope
public class ApiServices {
  private String catalogQuery;
  private String catalogCommand;
  private String basket;
  private String orderProcessing;
  private String analytics;
  private String rating;
}
