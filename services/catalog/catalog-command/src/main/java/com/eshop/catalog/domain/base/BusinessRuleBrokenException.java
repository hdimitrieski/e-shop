package com.eshop.catalog.domain.base;

public class BusinessRuleBrokenException extends RuntimeException {
  public BusinessRuleBrokenException(BusinessRule rule) {
    super(rule.message());
  }
}
