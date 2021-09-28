package com.eshop.catalog.domain.catalogitem;

import org.axonframework.modelling.command.Aggregate;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public interface CatalogItemRepository {
  Optional<CatalogItem> load(UUID id);

  Aggregate<CatalogItem> loadAggregate(UUID id);

  Aggregate<CatalogItem> save(Supplier<CatalogItem> createCatalogItem);
}
