package com.eshop.catalog.shared.events;

import com.eshop.catalog.shared.model.BrandDto;
import com.eshop.catalog.shared.model.CategoryDto;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CatalogItemCreated extends Event {
  private final String name;
  private final String description;
  private final Double price;
  private final String pictureFileName;
  private final Integer availableStock;
  private final CategoryDto category;
  private final BrandDto brand;

  public CatalogItemCreated(
      UUID id,
      String name,
      String description,
      Double price,
      String pictureFileName,
      Integer availableStock,
      CategoryDto category,
      BrandDto brand
  ) {
    super(id);
    this.name = name;
    this.description = description;
    this.price = price;
    this.pictureFileName = pictureFileName;
    this.availableStock = availableStock;
    this.category = category;
    this.brand = brand;
  }
}
