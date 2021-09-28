package com.eshop.catalog.domain.catalogitem;

import com.eshop.catalog.domain.base.CatalogDomainException;
import com.eshop.catalog.domain.base.ValueObject;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@ToString
public class ProductName extends ValueObject {
  private final String name;

  private ProductName(@NonNull String name) {
    if (isEmpty(name)) {
      throw new IllegalArgumentException("Product name cannot be empty");
    }

    if (name.length() < 5 || name.length() > 50) {
      throw new CatalogDomainException("Product name must be between 5 and 50 characters");
    }

    this.name = name;
  }

  public static ProductName of(String name) {
    return new ProductName(name);
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(name);
  }
}
