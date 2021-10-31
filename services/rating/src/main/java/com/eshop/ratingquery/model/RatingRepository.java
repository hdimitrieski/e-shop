package com.eshop.ratingquery.model;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RatingRepository extends CrudRepository<Rating, UUID> {
  Iterable<Rating> findByCatalogItemId(UUID catalogItemId);
}
