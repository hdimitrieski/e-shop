package com.eshop.imageservice.services;

import com.eshop.imageservice.models.ImageUploadRequest;
import com.eshop.imageservice.models.ImageUploadResponse;

public interface MinioService {
  ImageUploadResponse uploadImage(ImageUploadRequest request);
  void deleteImage(String name);
}
