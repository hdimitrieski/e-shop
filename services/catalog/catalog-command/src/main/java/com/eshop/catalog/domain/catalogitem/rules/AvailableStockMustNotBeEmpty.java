package com.eshop.catalog.domain.catalogitem.rules;

import com.eshop.catalog.domain.base.BusinessRule;
import com.eshop.catalog.domain.catalogitem.ProductName;
import com.eshop.catalog.domain.catalogitem.Units;

public record AvailableStockMustNotBeEmpty(
    ProductName name,
    Units availableStock
) implements BusinessRule {

  @Override
  public boolean broken() {
    return availableStock.isEmpty();
  }

  @Override
  public String message() {
    return "Empty stock, product item %s is sold out".formatted(name.getName());
  }
}
