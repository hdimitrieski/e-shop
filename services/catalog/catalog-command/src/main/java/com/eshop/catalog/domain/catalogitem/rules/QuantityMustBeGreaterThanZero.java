package com.eshop.catalog.domain.catalogitem.rules;

import com.eshop.catalog.domain.base.BusinessRule;
import com.eshop.catalog.domain.catalogitem.Units;

public record QuantityMustBeGreaterThanZero(
    Units quantity
) implements BusinessRule {

  @Override
  public boolean broken() {
    return quantity.isEmpty();
  }

  @Override
  public String message() {
    return "Item units desired should be greater than zero";
  }
}
