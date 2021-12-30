package com.eshop.ordering.api.application.models;

import com.eshop.ordering.api.application.dtos.OrderItemDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class BasketItem {
  @JsonProperty
  private String id;

  @JsonProperty
  @NotNull
  private UUID productId;

  @JsonProperty
  @NotNull
  private String productName;

  @JsonProperty
  @NotNull
  private Double unitPrice;

  @JsonProperty
  private Double oldUnitPrice;

  @JsonProperty
  @NotNull
  @Min(value = 1, message = "Invalid quantity")
  private Integer quantity;

  @JsonProperty
  private String pictureUrl;

  public static OrderItemDTO toOrderItemDTO(BasketItem item) {
    return new OrderItemDTO(
      item.getProductId(),
      item.getProductName(),
      item.getUnitPrice(),
      0D,
      item.getQuantity(),
      item.getPictureUrl()
    );
  }
}
