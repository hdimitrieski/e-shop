package com.eshop.imageservice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ImageUploadRequest {
  private final String name;
  private final String contentType;
  private final byte[] content;
}
