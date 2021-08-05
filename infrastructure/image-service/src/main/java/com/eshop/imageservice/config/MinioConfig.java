package com.eshop.imageservice.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
  @Value("${app.minio.credentials.name}")
  private String accessKey;
  @Value("${app.minio.credentials.secret}")
  private String accessSecret;
  @Value("${app.minio.url}")
  private String minioUrl;
  @Value("${app.minio.region}")
  private String minioRegion;

  @Bean
  public MinioClient minioClient() {
    try {
      return MinioClient.builder()
          .endpoint(minioUrl)
          .credentials(accessKey, accessSecret)
          .region(minioRegion)
          .build();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
