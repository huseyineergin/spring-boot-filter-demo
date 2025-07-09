package com.example.server.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.entity.Image;
import com.example.server.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

  private final ImageService imageService;

  @GetMapping("/{id}")
  public ResponseEntity<byte[]> getImage(@PathVariable UUID id) {
    Image image = imageService.getImageById(id);
    MediaType mediaType = MediaType.parseMediaType(image.getType());
    return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).body(image.getData());
  }

}