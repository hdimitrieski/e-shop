package com.eshop.imageservice.services;

import com.eshop.imageservice.exceptions.ImageUploadException;
import com.eshop.imageservice.models.ImageUploadRequest;
import com.eshop.imageservice.models.ImageUploadResponse;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@RequiredArgsConstructor
@Service
public class MinioServiceImpl implements MinioService {
  private final static Logger logger = LoggerFactory.getLogger(MinioServiceImpl.class);

  @Value("${app.minio.bucketname}")
  private String catalogImagesBucketName;

  private final MinioClient minioClient;

  @Override
  public ImageUploadResponse uploadImage(ImageUploadRequest request) {
    final var byteArrayInputStream = new ByteArrayInputStream(request.getContent());

    try {
      minioClient.putObject(
          PutObjectArgs.builder()
              .bucket(catalogImagesBucketName)
              .object(request.getName()).stream(byteArrayInputStream, request.getContent().length, -1)
              .contentType(request.getContentType())
              .build());
      return new ImageUploadResponse(request.getName());
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ImageUploadException("Cannot upload image %s".formatted(request.getName()));
    }
  }

  @Override
  public void deleteImage(String name) {
    try {
      minioClient.removeObject(
          RemoveObjectArgs.builder()
              .bucket(catalogImagesBucketName)
              .object(name)
              .build());
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

}
