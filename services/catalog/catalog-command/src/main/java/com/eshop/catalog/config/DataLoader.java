package com.eshop.catalog.config;

import com.eshop.catalog.application.commandbus.CatalogCommandBus;
import com.eshop.catalog.application.commands.createproduct.CreateProductCommand;
import com.eshop.catalog.domain.catalogitem.Brand;
import com.eshop.catalog.domain.catalogitem.BrandRepository;
import com.eshop.catalog.domain.catalogitem.Category;
import com.eshop.catalog.domain.catalogitem.CategoryRepository;
import com.eshop.catalog.shared.CatalogProfiles;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Insert test data.
 */
@RequiredArgsConstructor
@Component
@Profile(CatalogProfiles.DEV)
public class DataLoader implements ApplicationRunner {
  private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

  private final BrandRepository brandRepository;
  private final CategoryRepository categoryRepository;
  private final CatalogCommandBus commandBus;

  @Override
  public void run(ApplicationArguments args) {
    logger.info("Inserting test data...");

    if (brandRepository.findByName("Adidas").isPresent()) {
      logger.info("DB is already loaded. Abort inserting process...");
      return;
    }

    final var brandId1 = UUID.randomUUID();
    final var brandId2 = UUID.randomUUID();
    final var brandId3 = UUID.randomUUID();
    final var brandId4 = UUID.randomUUID();
    final var brandId5 = UUID.randomUUID();
    brandRepository.save(Brand.of(brandId1, "Adidas"));
    brandRepository.save(Brand.of(brandId2, "Endura"));
    brandRepository.save(Brand.of(brandId3, "Etnies"));
    brandRepository.save(Brand.of(brandId4, "Fox"));
    brandRepository.save(Brand.of(brandId5, "Giro"));

    final var categoryId1 = UUID.randomUUID();
    final var categoryId2 = UUID.randomUUID();
    categoryRepository.save(Category.of(categoryId1, "Shoes"));
    categoryRepository.save(Category.of(categoryId2, "Shirt"));

    commandBus.execute(new CreateProductCommand(
        "Adidas terrex-2",
        "Adidas terrex-2 shoes...",
        43.5,
        "adidas-shoes-2.png",
        13,
        brandId1,
        categoryId1
    ));
    commandBus.execute(new CreateProductCommand(
        "Adidas terrex",
        "Some adidas terrex shoes...",
        60.0,
        "adidas-shoes-1.png",
        10,
        brandId1,
        categoryId1
    ));
    commandBus.execute(new CreateProductCommand(
        "Adidas sh",
        "Adidas shoes...",
        51.2,
        "adidas-shoes-3.png",
        2,
        brandId1,
        categoryId1
    ));
    commandBus.execute(new CreateProductCommand(
        "Adidas sport",
        "Adidas shoes new...",
        31.7,
        "adidas-shoes-4.png",
        60,
        brandId1,
        categoryId1
    ));
    commandBus.execute(new CreateProductCommand(
        "Giro bike shoes",
        "Giro shoes...",
        50.2,
        "giro-shoes-1.png",
        35,
        brandId5,
        categoryId1
    ));
    commandBus.execute(new CreateProductCommand(
        "Giro street",
        "Giro street shoes...",
        34.8,
        "giro-shoes-2.png",
        1,
        brandId5,
        categoryId1
    ));
    commandBus.execute(new CreateProductCommand(
        "Giro vibram",
        "Giro vibram shoes...",
        90.0,
        "giro-shoes-3.png",
        10,
        brandId5,
        categoryId1
    ));
    commandBus.execute(new CreateProductCommand(
        "Etnies bike",
        "Etnies bike shoes...",
        46.2,
        "etnies-shoes-1.png",
        10,
        brandId3,
        categoryId1
    ));
    commandBus.execute(new CreateProductCommand(
        "Etnies street",
        "Etnies street shoes...",
        37.9,
        "etnies-shoes-2.png",
        12,
        brandId3,
        categoryId1
    ));
    commandBus.execute(new CreateProductCommand(
        "Endura shirt",
        "Endura shirt...",
        10.9,
        "endura-shirt-1.png",
        40,
        brandId2,
        categoryId2
    ));
    commandBus.execute(new CreateProductCommand(
        "Etnies street shirt",
        "Etnies shirt...",
        12.5,
        "etnies-shirt-1.png",
        15,
        brandId3,
        categoryId2
    ));
    commandBus.execute(new CreateProductCommand(
        "Etnies bike shirt",
        "Etnies bike...",
        13.4,
        "etnies-shirt-2.png",
        23,
        brandId3,
        categoryId2
    ));
    commandBus.execute(new CreateProductCommand(
        "Another Etnies shirt",
        "Etnies street shirt description...",
        14.6,
        "etnies-shirt-3.png",
        4,
        brandId3,
        categoryId2
    ));
    commandBus.execute(new CreateProductCommand(
        "Fox classic",
        "Fox classic shirt description...",
        20.0,
        "fox-shirt-1.png",
        16,
        brandId4,
        categoryId2
    ));
    commandBus.execute(new CreateProductCommand(
        "Fox racing",
        "Fox racing shirt description...",
        35.3,
        "fox-shirt-2.png",
        7,
        brandId4,
        categoryId2
    ));
    commandBus.execute(new CreateProductCommand(
        "Fox street",
        "Fox street shirt description...",
        13.5,
        "fox-shirt-3.png",
        23,
        brandId4,
        categoryId2
    ));
    commandBus.execute(new CreateProductCommand(
        "Fox bike",
        "Fox bike shirt description...",
        19.0,
        "fox-shirt-4.png",
        21,
        brandId4,
        categoryId2
    ));
  }
}
