package com.eshop.gqlgateway.services;

import com.eshop.gqlgateway.models.CatalogItemDto;
import com.eshop.gqlgateway.models.CatalogItemsPageDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CatalogApiService {
  Optional<CatalogItemDto> findById(UUID id);

  Optional<CatalogItemsPageDto> findAll(Integer page, Integer pageSize);

  List<CatalogItemDto> findByIds(List<UUID> ids);
}
