package com.eshop.catalog;

import com.eshop.catalog.model.*;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
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

    var brands = Lists.newArrayList(catalogBrandRepository.saveAll(List.of(
        CatalogBrand.builder().brand("Nike").build(),
        CatalogBrand.builder().brand("Addidas").build(),
        CatalogBrand.builder().brand("Salomon").build(),
        CatalogBrand.builder().brand("Jones").build()
    )));

    var types = Lists.newArrayList(catalogTypeRepository.saveAll(List.of(
        CatalogType.builder().type("Gloves").build(),
        CatalogType.builder().type("Shoes").build(),
        CatalogType.builder().type("Pants").build(),
        CatalogType.builder().type("Shirts").build()
    )));

    catalogItemRepository.saveAll(List.of(
        CatalogItem.builder()
            .availableStock(10)
            .description("Short description...")
            .maxStockThreshold(20)
            .name("Nike shoes 1.5")
            .price(new BigDecimal("23.4"))
            .restockThreshold(5)
            .catalogBrand(brands.get(0))
            .catalogType(types.get(0))
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
            .catalogBrand(brands.get(0))
            .catalogType(types.get(1))
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("43.4"))
            .restockThreshold(6)
            .catalogBrand(brands.get(2))
            .catalogType(types.get(1))
            .build()
    ));

  }
}
