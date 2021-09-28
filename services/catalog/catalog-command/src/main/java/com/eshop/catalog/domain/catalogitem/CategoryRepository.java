package com.eshop.catalog.domain.catalogitem;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
  Category save(Category category);

  Optional<Category> findByName(String name);

  Optional<Category> findById(UUID id);
}
