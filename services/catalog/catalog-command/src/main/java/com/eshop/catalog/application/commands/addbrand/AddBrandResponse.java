package com.eshop.catalog.application.commands.addbrand;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class AddBrandResponse {
  private final String brandName;
}
