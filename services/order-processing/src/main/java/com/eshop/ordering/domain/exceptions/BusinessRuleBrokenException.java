package com.eshop.ordering.domain.exceptions;

import com.eshop.ordering.domain.base.BusinessRule;

public class BusinessRuleBrokenException extends RuntimeException {
  public BusinessRuleBrokenException(BusinessRule rule) {
    super(rule.message());
  }
}
