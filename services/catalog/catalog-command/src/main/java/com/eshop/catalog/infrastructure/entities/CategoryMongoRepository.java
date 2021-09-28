package com.eshop.catalog.infrastructure.entities;

import com.eshop.catalog.domain.catalogitem.Category;
import com.eshop.catalog.domain.catalogitem.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CategoryMongoRepository implements CategoryRepository {
  private final CategoryEntityConverter categoryEntityConverter;
  private final MongoTemplate mongoTemplate;

  @Override
  public Category save(Category category) {
    final var categoryEntity = mongoTemplate.save(categoryEntityConverter.toEntity(category));
    return categoryEntityConverter.fromEntity(categoryEntity);
  }

  @Override
  public Optional<Category> findByName(String name) {
    final var query = new Query();
    query.addCriteria(Criteria.where("name").is(name));
    return Optional.ofNullable(mongoTemplate.findOne(query, CategoryEntity.class))
        .map(categoryEntityConverter::fromEntity);
  }

  @Override
  public Optional<Category> findById(UUID id) {
    final var query = new Query();
    query.addCriteria(Criteria.where("categoryId").is(id));
    return Optional.ofNullable(mongoTemplate.findOne(query, CategoryEntity.class))
        .map(categoryEntityConverter::fromEntity);
  }
}
