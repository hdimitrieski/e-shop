package com.eshop.catalog.infrastructure.entities;

import com.eshop.catalog.domain.catalogitem.Brand;
import com.eshop.catalog.domain.catalogitem.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class BrandMongoRepository implements BrandRepository {
  private final BrandEntityConverter brandEntityConverter;
  private final MongoTemplate mongoTemplate;

  @Override
  public Brand save(Brand brand) {
    final var brandEntity = mongoTemplate.save(brandEntityConverter.toEntity(brand));
    return brandEntityConverter.fromEntity(brandEntity);
  }

  @Override
  public Optional<Brand> findByName(String name) {
    final var query = new Query();
    query.addCriteria(Criteria.where("name").is(name));
    final var brandEntity = mongoTemplate.findOne(query, BrandEntity.class);
    return Optional.ofNullable(brandEntity)
        .map(brandEntityConverter::fromEntity);
  }

  @Override
  public Optional<Brand> findById(UUID id) {
    final var query = new Query();
    query.addCriteria(Criteria.where("brandId").is(id));
    final var brandEntity = mongoTemplate.findOne(query, BrandEntity.class);
    return Optional.ofNullable(brandEntity)
        .map(brandEntityConverter::fromEntity);
  }
}
