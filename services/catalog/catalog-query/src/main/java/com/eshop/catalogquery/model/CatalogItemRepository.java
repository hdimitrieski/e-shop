package com.eshop.catalogquery.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CatalogItemRepository
    extends PagingAndSortingRepository<CatalogItem, UUID>, JpaSpecificationExecutor<CatalogItem> {
  Page<CatalogItem> findAllByName(String name, Pageable pageable);
}
