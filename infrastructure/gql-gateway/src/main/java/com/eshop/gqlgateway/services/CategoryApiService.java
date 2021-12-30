package com.eshop.gqlgateway.services;

import com.eshop.gqlgateway.models.CategoryDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryApiService {
  Optional<CategoryDto> findById(UUID id);

  List<CategoryDto> findAll();
}
