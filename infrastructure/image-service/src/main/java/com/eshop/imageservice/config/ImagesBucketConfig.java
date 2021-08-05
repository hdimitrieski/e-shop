package com.eshop.imageservice.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Order(1)
@RequiredArgsConstructor
@Configuration
public class ImagesBucketConfig implements ApplicationRunner {
  private static final Logger logger = LoggerFactory.getLogger(ImageImporter.class);

  private final MinioClient minioClient;

  @Value("${app.minio.bucketname}")
  private String catalogImagesBucketName;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(catalogImagesBucketName).build())) {
      minioClient.makeBucket(MakeBucketArgs.builder()
          .bucket(catalogImagesBucketName)
          .build());
      logger.info("Bucket '%s' created.".formatted(catalogImagesBucketName));
    } else {
      logger.info("Bucket '%s' already exists.".formatted(catalogImagesBucketName));
    }
  }
}
