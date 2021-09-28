package com.eshop.catalog.domain.catalogitem;

import com.eshop.catalog.domain.base.ValueObject;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@ToString
public class Category extends ValueObject {
  private final String name;
  private final UUID categoryId;

  private Category(@NotNull UUID categoryId, @NonNull String name) {
    if (isEmpty(name)) {
      throw new IllegalArgumentException("Category name cannot be empty");
    }
    if (isNull(categoryId)) {
      throw new IllegalArgumentException("Brand id cannot be null");
    }

    this.name = name;
    this.categoryId = categoryId;
  }

  public static Category of(UUID categoryId, String name) {
    return new Category(categoryId, name);
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(name);
  }
}
