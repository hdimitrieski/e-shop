package com.eshop.catalog.infrastructure.entities;

import com.eshop.catalog.domain.catalogitem.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityConverter implements EntityConverter<CategoryEntity, Category> {
  @Override
  public CategoryEntity toEntity(Category category) {
    return CategoryEntity.builder()
        .categoryId(category.getCategoryId())
        .name(category.getName())
        .build();
  }

  @Override
  public Category fromEntity(CategoryEntity entity) {
    return Category.of(entity.getCategoryId(), entity.getName());
  }
}
