package com.eshop.catalog;

import com.eshop.catalog.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
//@Component
class Seed {
  private final CatalogBrandRepository catalogBrandRepository;
  private final CatalogTypeRepository catalogTypeRepository;
  private final CatalogItemRepository catalogItemRepository;

  @PostConstruct
  void init() {
    System.out.println("Initializing data...");

    if (catalogBrandRepository.findAll().iterator().hasNext()) {
      return;
    }

    var brands = catalogBrandRepository.saveAll(List.of(
        CatalogBrand.builder().brand("Nike").build(),
        CatalogBrand.builder().brand("Addidas").build(),
        CatalogBrand.builder().brand("Salomon").build(),
        CatalogBrand.builder().brand("Jones").build()
    ));

    var types = catalogTypeRepository.saveAll(List.of(
        CatalogType.builder().type("Gloves").build(),
        CatalogType.builder().type("Shoes").build(),
        CatalogType.builder().type("Pants").build(),
        CatalogType.builder().type("Shirts").build()
    ));

    catalogItemRepository.saveAll(List.of(
        CatalogItem.builder()
            .availableStock(10)
            .description("Short description...")
            .maxStockThreshold(20)
            .name("Nike shoes 1.5")
            .price(new BigDecimal("23.4"))
            .restockThreshold(5)
            .catalogBrand(brands.iterator().next())
            .catalogType(types.iterator().next())
            .build()
    ));

    catalogItemRepository.saveAll(List.of(
        CatalogItem.builder()
            .availableStock(10)
            .description("Short description...")
            .maxStockThreshold(20)
            .name("Addidas shirt 1.5")
            .price(new BigDecimal("43.4"))
            .restockThreshold(5)
            .catalogBrand(brands.iterator().next())
            .catalogType(types.iterator().next())
            .build()
    ));

  }
}
