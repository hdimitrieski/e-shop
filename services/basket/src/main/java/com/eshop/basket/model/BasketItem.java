package com.eshop.basket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class BasketItem implements Serializable {
  @Setter
  private String id;
  private Long productId;
  private String productName;
  @Setter
  private Double unitPrice;
  @Setter
  private Double oldUnitPrice;
  @Min(value = 1, message = "Invalid number of units")
  private Integer quantity;
  private String pictureUrl;
}
