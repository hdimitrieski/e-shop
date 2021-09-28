package com.eshop.catalog.domain.base;

public interface BusinessRule {
  boolean broken();

  String message();
}
