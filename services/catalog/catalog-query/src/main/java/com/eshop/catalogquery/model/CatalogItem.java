package com.eshop.catalogquery.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@Table(name = "catalog_item")
public class CatalogItem {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "picture_file_name")
  private String pictureFileName;

  // Quantity in stock
  @Column(name = "available_stock")
  private Integer availableStock;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "brand_id")
  private Brand brand;

}

