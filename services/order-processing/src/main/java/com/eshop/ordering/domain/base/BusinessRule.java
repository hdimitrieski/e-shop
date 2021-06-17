package com.eshop.ordering.domain.base;

public interface BusinessRule {
  boolean broken();

  String message();
}
