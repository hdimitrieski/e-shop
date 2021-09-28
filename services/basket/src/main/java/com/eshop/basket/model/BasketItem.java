package com.eshop.basket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class BasketItem implements Serializable {
  @Setter
  private String id;
  @NotNull(message = "Product id is required")
  private UUID productId;
  @NotEmpty(message = "Product name is required")
  private String productName;
  @Setter
  private Double unitPrice;
  @Setter
  private Double oldUnitPrice;
  @Min(value = 1, message = "Invalid number of units")
  private Integer quantity;
  private String pictureUrl;
}
