package com.eshop.catalogquery.config;

import com.eshop.catalogquery.model.*;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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
      logger.info("Database is already loaded. Abort the inserting process...");
      return;
    }

    var brands = Lists.newArrayList(brandRepository.saveAll(List.of(
        Brand.builder().id(UUID.randomUUID()).name("Adidas").build(),
        Brand.builder().id(UUID.randomUUID()).name("Endura").build(),
        Brand.builder().id(UUID.randomUUID()).name("Etnies").build(),
        Brand.builder().id(UUID.randomUUID()).name("Fox").build(),
        Brand.builder().id(UUID.randomUUID()).name("Giro").build()
    )));

    var types = Lists.newArrayList(categoryRepository.saveAll(List.of(
        Category.builder().id(UUID.randomUUID()).name("Shoes").build(),
        Category.builder().id(UUID.randomUUID()).name("Shirt").build()
    )));

    List<CatalogItem> catalogItems = List.of(
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(10)
            .description("Some adidas terrex shoes...")
            .name("Adidas terrex")
            .price(60.0)
            .brand(brands.get(0))
            .category(types.get(0))
            .pictureFileName("adidas-shoes-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(13)
            .description("Adidas terrex-2 shoes...")
            .name("Adidas terrex-2")
            .price(43.5)
            .brand(brands.get(0))
            .category(types.get(0))
            .pictureFileName("adidas-shoes-2.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(2)
            .description("Adidas shoes...")
            .name("Adidas sh")
            .price(51.2)
            .brand(brands.get(0))
            .category(types.get(0))
            .pictureFileName("adidas-shoes-3.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(60)
            .description("Adidas shoes new...")
            .name("Adidas sport")
            .price(31.7)
            .brand(brands.get(0))
            .category(types.get(0))
            .pictureFileName("adidas-shoes-4.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(35)
            .description("Giro shoes...")
            .name("Giro bike shoes")
            .price(50.2)
            .brand(brands.get(4))
            .category(types.get(0))
            .pictureFileName("giro-shoes-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(1)
            .description("Giro street shoes...")
            .name("Giro street")
            .price(34.8)
            .brand(brands.get(4))
            .category(types.get(0))
            .pictureFileName("giro-shoes-2.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(10)
            .description("Giro vibram shoes...")
            .name("Giro vibram")
            .price(90.0)
            .brand(brands.get(0))
            .category(types.get(0))
            .pictureFileName("giro-shoes-3.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(10)
            .description("Etnies bike shoes...")
            .name("Etnies bike")
            .price(46.2)
            .brand(brands.get(2))
            .category(types.get(0))
            .pictureFileName("etnies-shoes-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(12)
            .description("Etnies street shoes...")
            .name("Etnies street")
            .price(37.9)
            .brand(brands.get(2))
            .category(types.get(0))
            .pictureFileName("etnies-shoes-2.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(40)
            .description("Endura shirt...")
            .name("Endura shirt")
            .price(10.9)
            .brand(brands.get(1))
            .category(types.get(1))
            .pictureFileName("endura-shirt-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(15)
            .description("Etnies shirt...")
            .name("Etnies street shirt")
            .price(10.5)
            .brand(brands.get(2))
            .category(types.get(1))
            .pictureFileName("etnies-shirt-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(54)
            .description("Etnies bike shirt...")
            .name("Etnies bike")
            .price(12.7)
            .brand(brands.get(2))
            .category(types.get(0))
            .pictureFileName("etnies-shirt-2.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(3)
            .description("Etnies street shirt description...")
            .name("Nike shoes")
            .price(16.4)
            .brand(brands.get(2))
            .category(types.get(1))
            .pictureFileName("etnies-shirt-3.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(16)
            .description("Fox classic shirt description...")
            .name("Fox classic")
            .price(20.0)
            .brand(brands.get(3))
            .category(types.get(1))
            .pictureFileName("fox-shirt-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(100)
            .description("Fox racing shirt description...")
            .name("Fox racing shirt")
            .price(21.25)
            .brand(brands.get(3))
            .category(types.get(1))
            .pictureFileName("fox-shirt-2.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(60)
            .description("Fox street shirt description...")
            .name("Fox street shirt")
            .price(13.5)
            .brand(brands.get(3))
            .category(types.get(1))
            .pictureFileName("fox-shirt-3.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(5)
            .description("Something...")
            .name("Puma Shoes")
            .price(14.0)
            .brand(brands.get(3))
            .category(types.get(1))
            .pictureFileName("fox-shirt-4.png")
            .build()
    );
    catalogItemRepository.saveAll(catalogItems);
  }
}
