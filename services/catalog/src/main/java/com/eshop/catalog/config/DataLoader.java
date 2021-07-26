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

  private final BrandRepository brandRepository;
  private final CategoryRepository categoryRepository;
  private final CatalogItemRepository catalogItemRepository;

  @Override
  public void run(ApplicationArguments args) {
    logger.info("Inserting test data...");

    if (brandRepository.findAll().iterator().hasNext()) {
      return;
    }

    var brands = Lists.newArrayList(brandRepository.saveAll(List.of(
        Brand.builder().name("Adidas").build(),
        Brand.builder().name("Endura").build(),
        Brand.builder().name("Etnies").build(),
        Brand.builder().name("Fox").build(),
        Brand.builder().name("Giro").build()
    )));

    var types = Lists.newArrayList(categoryRepository.saveAll(List.of(
        Category.builder().name("Shoes").build(),
        Category.builder().name("Shirt").build()
    )));

    catalogItemRepository.saveAll(List.of(
        CatalogItem.builder()
            .availableStock(10)
            .description("Some adidas terrex shoes...")
            .maxStockThreshold(20)
            .name("Adidas terrex")
            .price(new BigDecimal("60"))
            .restockThreshold(5)
            .brand(brands.get(0))
            .category(types.get(0))
            .pictureFileName("adidas-shoes-1.png")
            .build(),
        CatalogItem.builder()
            .availableStock(13)
            .description("Adidas terrex-2 shoes...")
            .maxStockThreshold(20)
            .name("Adidas terrex-2")
            .price(new BigDecimal("43.5"))
            .restockThreshold(5)
            .brand(brands.get(0))
            .category(types.get(0))
            .pictureFileName("adidas-shoes-2.png")
            .build(),
        CatalogItem.builder()
            .availableStock(2)
            .description("Adidas shoes...")
            .maxStockThreshold(40)
            .name("Adidas sh")
            .price(new BigDecimal("51"))
            .restockThreshold(10)
            .brand(brands.get(0))
            .category(types.get(0))
            .pictureFileName("adidas-shoes-3.png")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Adidas shoes new...")
            .maxStockThreshold(80)
            .name("Adidas sport")
            .price(new BigDecimal("31.7"))
            .restockThreshold(6)
            .brand(brands.get(0))
            .category(types.get(0))
            .pictureFileName("adidas-shoes-4.png")
            .build(),
        CatalogItem.builder()
            .availableStock(35)
            .description("Giro shoes...")
            .maxStockThreshold(60)
            .name("Giro bike shoes")
            .price(new BigDecimal("50.2"))
            .restockThreshold(6)
            .brand(brands.get(4))
            .category(types.get(0))
            .pictureFileName("giro-shoes-1.png")
            .build(),
        CatalogItem.builder()
            .availableStock(1)
            .description("Giro street shoes...")
            .maxStockThreshold(20)
            .name("Giro street")
            .price(new BigDecimal("34.8"))
            .restockThreshold(6)
            .brand(brands.get(4))
            .category(types.get(0))
            .pictureFileName("giro-shoes-2.png")
            .build(),
        CatalogItem.builder()
            .availableStock(10)
            .description("Giro vibram shoes...")
            .maxStockThreshold(20)
            .name("Giro vibram")
            .price(new BigDecimal("90"))
            .restockThreshold(5)
            .brand(brands.get(0))
            .category(types.get(0))
            .pictureFileName("giro-shoes-3.png")
            .build(),
        CatalogItem.builder()
            .availableStock(10)
            .description("Etnies bike shoes...")
            .maxStockThreshold(20)
            .name("Etnies bike")
            .price(new BigDecimal("46.2"))
            .restockThreshold(5)
            .brand(brands.get(2))
            .category(types.get(0))
            .pictureFileName("etnies-shoes-1.png")
            .build(),
        CatalogItem.builder()
            .availableStock(12)
            .description("Etnies street shoes...")
            .maxStockThreshold(30)
            .name("Etnies street")
            .price(new BigDecimal("37.9"))
            .restockThreshold(6)
            .brand(brands.get(2))
            .category(types.get(0))
            .pictureFileName("etnies-shoes-2.png")
            .build(),
        CatalogItem.builder()
            .availableStock(40)
            .description("Endura shirt...")
            .maxStockThreshold(90)
            .name("Endura shirt")
            .price(new BigDecimal("10.9"))
            .restockThreshold(20)
            .brand(brands.get(1))
            .category(types.get(1))
            .pictureFileName("endura-shirt-1.png")
            .build(),
        CatalogItem.builder()
            .availableStock(15)
            .description("Etnies shirt...")
            .maxStockThreshold(20)
            .name("Etnies street shirt")
            .price(new BigDecimal("10.5"))
            .restockThreshold(6)
            .brand(brands.get(2))
            .category(types.get(1))
            .pictureFileName("etnies-shirt-1.png")
            .build(),
        CatalogItem.builder()
            .availableStock(54)
            .description("Etnies bike shirt...")
            .maxStockThreshold(100)
            .name("Etnies bike")
            .price(new BigDecimal("12.7"))
            .restockThreshold(6)
            .brand(brands.get(2))
            .category(types.get(0))
            .pictureFileName("etnies-shirt-2.png")
            .build(),
        CatalogItem.builder()
            .availableStock(3)
            .description("Etnies street shirt description...")
            .maxStockThreshold(20)
            .name("Nike shoes")
            .price(new BigDecimal("16.4"))
            .restockThreshold(5)
            .brand(brands.get(2))
            .category(types.get(1))
            .pictureFileName("etnies-shirt-3.png")
            .build(),
        CatalogItem.builder()
            .availableStock(16)
            .description("Fox classic shirt description...")
            .maxStockThreshold(20)
            .name("Fox classic")
            .price(new BigDecimal("20"))
            .restockThreshold(20)
            .brand(brands.get(3))
            .category(types.get(1))
            .pictureFileName("fox-shirt-1.png")
            .build(),
        CatalogItem.builder()
            .availableStock(100)
            .description("Fox racing shirt description...")
            .maxStockThreshold(200)
            .name("Fox racing shirt")
            .price(new BigDecimal("21.25"))
            .restockThreshold(40)
            .brand(brands.get(3))
            .category(types.get(1))
            .pictureFileName("fox-shirt-2.png")
            .build(),
        CatalogItem.builder()
            .availableStock(60)
            .description("Fox street shirt description...")
            .maxStockThreshold(80)
            .name("Fox street shirt")
            .price(new BigDecimal("13.5"))
            .restockThreshold(6)
            .brand(brands.get(3))
            .category(types.get(1))
            .pictureFileName("fox-shirt-3.png")
            .build(),
        CatalogItem.builder()
            .availableStock(5)
            .description("Something...")
            .maxStockThreshold(100)
            .name("Puma Shoes")
            .price(new BigDecimal("14"))
            .restockThreshold(20)
            .brand(brands.get(3))
            .category(types.get(1))
            .pictureFileName("fox-shirt-4.png")
            .build()
    ));
  }
}
