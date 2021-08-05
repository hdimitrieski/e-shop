package com.eshop.imageservice.config;

import com.eshop.imageservice.models.ImageUploadRequest;
import com.eshop.imageservice.services.MinioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@Profile("dev")
public class ImageImporter implements ApplicationRunner {
  private static final Logger logger = LoggerFactory.getLogger(ImageImporter.class);

  private final ResourceLoader resourceLoader;
  private final MinioService minioService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    final var resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
    final var images = Arrays.asList(resourcePatternResolver.getResources("classpath:images/**"));
    uploadImages(images);
  }

  private void uploadImages(List<Resource> images) {
    images.forEach(this::uploadImage);
  }

  private void uploadImage(Resource image) {
    try {
      logger.info("Uploading image %s".formatted(image.getFilename()));
      final var content = image.getInputStream().readAllBytes();
      minioService.uploadImage(new ImageUploadRequest(image.getFilename(), "image/png", content));
    } catch (IOException e) {
      logger.error("Failed to read image %s".formatted(image.getFilename()), e);
    }
  }

}
