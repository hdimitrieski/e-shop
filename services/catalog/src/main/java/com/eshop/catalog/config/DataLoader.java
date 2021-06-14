package com.eshop.catalog.config;

import com.eshop.catalog.model.*;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Insert test data.
 */
@RequiredArgsConstructor
@Component
@Profile("dev")
public class DataLoader implements ApplicationRunner {
  private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

  private final CatalogBrandRepository catalogBrandRepository;
  private final CatalogTypeRepository catalogTypeRepository;
  private final CatalogItemRepository catalogItemRepository;

  @Override
  public void run(ApplicationArguments args) {
    logger.info("Inserting test data...");

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
            .name("Nike shoes")
            .price(new BigDecimal("23.4"))
            .restockThreshold(5)
            .catalogBrand(brands.get(0))
            .catalogType(types.get(0))
            .pictureFileName("arch")
            .build(),
        CatalogItem.builder()
            .availableStock(10)
            .description("Short description...")
            .maxStockThreshold(20)
            .name("Addidas shirt")
            .price(new BigDecimal("43.4"))
            .restockThreshold(5)
            .catalogBrand(brands.get(1))
            .catalogType(types.get(1))
            .pictureFileName("tech")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("78.4"))
            .restockThreshold(6)
            .catalogBrand(brands.get(2))
            .catalogType(types.get(2))
            .pictureFileName("animals")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("44.4"))
            .restockThreshold(6)
            .catalogBrand(brands.get(3))
            .catalogType(types.get(3))
            .pictureFileName("nature")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("43.4"))
            .restockThreshold(6)
            .catalogBrand(brands.get(0))
            .catalogType(types.get(1))
            .pictureFileName("tech")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("43.4"))
            .restockThreshold(6)
            .catalogBrand(brands.get(1))
            .catalogType(types.get(0))
            .pictureFileName("people")
            .build(),
        CatalogItem.builder()
            .availableStock(10)
            .description("Short description...")
            .maxStockThreshold(20)
            .name("Nike shoes")
            .price(new BigDecimal("23.4"))
            .restockThreshold(5)
            .catalogBrand(brands.get(0))
            .catalogType(types.get(0))
            .pictureFileName("arch")
            .build(),
        CatalogItem.builder()
            .availableStock(10)
            .description("Short description...")
            .maxStockThreshold(20)
            .name("Addidas shirt")
            .price(new BigDecimal("44.2"))
            .restockThreshold(5)
            .catalogBrand(brands.get(1))
            .catalogType(types.get(1))
            .pictureFileName("tech")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("36.26"))
            .restockThreshold(6)
            .catalogBrand(brands.get(2))
            .catalogType(types.get(2))
            .pictureFileName("animals")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("14.4"))
            .restockThreshold(6)
            .catalogBrand(brands.get(3))
            .catalogType(types.get(3))
            .pictureFileName("nature")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("55.4"))
            .restockThreshold(6)
            .catalogBrand(brands.get(0))
            .catalogType(types.get(1))
            .pictureFileName("tech")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("124.4"))
            .restockThreshold(6)
            .catalogBrand(brands.get(1))
            .catalogType(types.get(0))
            .pictureFileName("people")
            .build(),
        CatalogItem.builder()
            .availableStock(10)
            .description("Short description...")
            .maxStockThreshold(20)
            .name("Nike shoes")
            .price(new BigDecimal("23.4"))
            .restockThreshold(5)
            .catalogBrand(brands.get(0))
            .catalogType(types.get(0))
            .pictureFileName("arch")
            .build(),
        CatalogItem.builder()
            .availableStock(10)
            .description("Short description...")
            .maxStockThreshold(20)
            .name("Addidas shirt")
            .price(new BigDecimal("43.4"))
            .restockThreshold(5)
            .catalogBrand(brands.get(1))
            .catalogType(types.get(1))
            .pictureFileName("tech")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("74.25"))
            .restockThreshold(6)
            .catalogBrand(brands.get(2))
            .catalogType(types.get(2))
            .pictureFileName("animals")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("25.45"))
            .restockThreshold(6)
            .catalogBrand(brands.get(3))
            .catalogType(types.get(3))
            .pictureFileName("nature")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("74.36"))
            .restockThreshold(6)
            .catalogBrand(brands.get(0))
            .catalogType(types.get(1))
            .pictureFileName("tech")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("114.85"))
            .restockThreshold(6)
            .catalogBrand(brands.get(1))
            .catalogType(types.get(0))
            .pictureFileName("people")
            .build(),
        CatalogItem.builder()
            .availableStock(10)
            .description("Short description...")
            .maxStockThreshold(20)
            .name("Nike shoes")
            .price(new BigDecimal("96.2"))
            .restockThreshold(5)
            .catalogBrand(brands.get(0))
            .catalogType(types.get(1))
            .pictureFileName("arch")
            .build(),
        CatalogItem.builder()
            .availableStock(10)
            .description("Short description...")
            .maxStockThreshold(20)
            .name("Addidas shirt")
            .price(new BigDecimal("45.1"))
            .restockThreshold(5)
            .catalogBrand(brands.get(3))
            .catalogType(types.get(2))
            .pictureFileName("tech")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("87.2"))
            .restockThreshold(6)
            .catalogBrand(brands.get(0))
            .catalogType(types.get(0))
            .pictureFileName("animals")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("63.2"))
            .restockThreshold(6)
            .catalogBrand(brands.get(1))
            .catalogType(types.get(1))
            .pictureFileName("nature")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("44.02"))
            .restockThreshold(6)
            .catalogBrand(brands.get(3))
            .catalogType(types.get(0))
            .pictureFileName("tech")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Something...")
            .maxStockThreshold(20)
            .name("Puma Shoes")
            .price(new BigDecimal("11.26"))
            .restockThreshold(6)
            .catalogBrand(brands.get(2))
            .catalogType(types.get(1))
            .pictureFileName("people")
            .build()
    ));
  }
}
