package com.example.server.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.server.dto.response.ImageResponse;
import com.example.server.entity.Image;
import com.example.server.repository.ImageRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

  private final ImageRepository imageRepository;

  public Image getImageById(UUID id) {
    return imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Image not found."));
  }

  public ImageResponse mapToResponse(Image image) {
    return ImageResponse.builder()
        .id(image.getId())
        .type(image.getType())
        .build();
  }

}