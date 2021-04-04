package com.eshop.basket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo refreshing configuration properties.
 *
 * POST localhost:8888/monitor?path=basket
 *
 * OR
 *
 * POST localhost:8888/monitor
 * body x-www-form-urlencoded
 * path=basket
 */
@RestController
@RequiredArgsConstructor
public class TestController {
  private final TestConfig testConfig;

  @GetMapping(value = "/test")
  public TestConfig getConfigData() {
    return this.testConfig;
  }
}
