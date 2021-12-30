package com.eshop.gqlgateway.services;

import com.eshop.gqlgateway.models.BrandDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrandApiService {
  Optional<BrandDto> findById(UUID id);

  List<BrandDto> findAll();
}
