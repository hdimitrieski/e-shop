package com.eshop.catalog.application.integrationevents.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConfirmedOrderStockItem {
  private UUID productId;
  private Boolean hasStock;
}
