package com.eshop.imageservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageUploadResponse {
  @JsonProperty("fileName")
  private final String fileName;
}
