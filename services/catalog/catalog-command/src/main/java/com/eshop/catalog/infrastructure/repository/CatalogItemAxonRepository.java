package com.eshop.catalog.infrastructure.repository;

import com.eshop.catalog.domain.catalogitem.CatalogItem;
import com.eshop.catalog.domain.catalogitem.CatalogItemRepository;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.spring.config.SpringAxonConfiguration;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@Repository
public class CatalogItemAxonRepository implements CatalogItemRepository {

  private final org.axonframework.modelling.command.Repository<CatalogItem> repository;

  CatalogItemAxonRepository(SpringAxonConfiguration axonConfiguration) {
    repository = axonConfiguration.getObject().repository(CatalogItem.class);
  }

  @Override
  public Optional<CatalogItem> load(UUID id) {
    try {
      return Optional.of(repository.load(id.toString()).invoke(Function.identity()));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public Aggregate<CatalogItem> loadAggregate(UUID id) {
    return repository.load(id.toString());
  }

  @Override
  public Aggregate<CatalogItem> save(Supplier<CatalogItem> createCatalogItem) {
    try {
      return repository.newInstance(createCatalogItem::get);
    } catch (Exception e) {
      throw new RuntimeException("Cannot create catalog item");
    }
  }
}
