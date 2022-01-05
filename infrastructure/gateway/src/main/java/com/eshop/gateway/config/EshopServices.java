package com.eshop.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.services")
@Data
public class EshopServices {
  private String catalogQuery;
  private String catalogCommand;
  private String orderProcessing;
  private String basket;
  private String analytics;
  private String rating;
}
