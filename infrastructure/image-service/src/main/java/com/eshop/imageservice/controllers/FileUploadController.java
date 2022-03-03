package com.eshop.imageservice.controllers;

import com.eshop.imageservice.models.ImageUploadRequest;
import com.eshop.imageservice.models.ImageUploadResponse;
import com.eshop.imageservice.services.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FileUploadController {

  private final MinioService minioService;

  @CrossOrigin({"${app.client.client-address.ng}", "${app.client.client-address.react}"})
  @PostMapping("/images/upload")
  public ResponseEntity<ImageUploadResponse> uploadFile(
      @RequestBody byte[] file,
      @RequestParam("fileName") String fileName,
      @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType
  ) {
    final var response = minioService.uploadImage(new ImageUploadRequest(fileName, contentType, file));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

}
