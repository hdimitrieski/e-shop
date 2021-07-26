package com.eshop.catalog.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CatalogItemRepository extends PagingAndSortingRepository<CatalogItem, Long>, JpaSpecificationExecutor<CatalogItem> {
  Page<CatalogItem> findAllByName(String name, Pageable pageable);
}
