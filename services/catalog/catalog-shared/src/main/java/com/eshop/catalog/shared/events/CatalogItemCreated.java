package com.eshop.catalog.shared.events;

import com.eshop.catalog.shared.model.BrandDto;
import com.eshop.catalog.shared.model.CategoryDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class CatalogItemCreated extends Event {
  private String name;
  private String description;
  private Double price;
  private String pictureFileName;
  private Integer availableStock;
  private CategoryDto category;
  private BrandDto brand;

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
