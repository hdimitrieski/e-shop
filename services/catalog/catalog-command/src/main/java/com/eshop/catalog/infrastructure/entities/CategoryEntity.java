package com.eshop.catalog.infrastructure.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Document("category")
public class CategoryEntity {

  @Id
  private String id;

  private UUID categoryId;

  private String name;
}
