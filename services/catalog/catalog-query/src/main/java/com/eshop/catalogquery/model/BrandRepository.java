package com.eshop.catalogquery.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends CrudRepository<Brand, UUID> {
  Optional<Brand> findByName(String name);
}
