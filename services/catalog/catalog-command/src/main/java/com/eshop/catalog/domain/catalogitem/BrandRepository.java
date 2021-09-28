package com.eshop.catalog.domain.catalogitem;

import java.util.Optional;
import java.util.UUID;

public interface BrandRepository {
  Brand save(Brand brand);

  Optional<Brand> findByName(String name);

  Optional<Brand> findById(UUID id);
}
