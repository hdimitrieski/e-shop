package com.eshop.catalog.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * Custom health check.
 */
public class CatalogHealthCheck implements HealthIndicator {
  @Override
  public Health health() {
    return Health.up().build();
  }
}
