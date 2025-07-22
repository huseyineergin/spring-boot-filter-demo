package com.example.server.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.server.dto.request.filter.PlatformFilterRequest;
import com.example.server.dto.request.genre.UpdateGenreRequest;
import com.example.server.dto.request.platform.CreatePlatformRequest;
import com.example.server.dto.response.PlatformResponse;
import com.example.server.entity.Platform;
import com.example.server.repository.PlatformRepository;
import com.example.server.repository.specification.PlatformSpecification;
import com.example.server.utils.SlugUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlatformService {

  private final PlatformRepository platformRepository;

  public Platform createPlatform(CreatePlatformRequest request) {
    Platform platform = Platform.builder()
        .name(request.getName())
        .category(request.getCategory())
        .slug(SlugUtils.generateSlug(request.getName()))
        .build();
    return platformRepository.save(platform);
  }

  public List<Platform> getAllPlatforms(PlatformFilterRequest filters) {
    Specification<Platform> spec = PlatformSpecification.hasCategory(filters.getCategory());

    return platformRepository.findAll(spec);
  }

  public Platform getPlatformById(UUID id) {
    return platformRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Platform not found."));
  }

  public Platform updatePlatformById(UUID id, UpdateGenreRequest request) {
    Platform existingPlatform = getPlatformById(id);

    existingPlatform.setName(request.getName());
    existingPlatform.setCategory(request.getCategory());

    return platformRepository.save(existingPlatform);
  }

  public void deletePlatformById(UUID id) {
    if (!platformRepository.existsById(id)) {
      throw new EntityNotFoundException("Platform not found.");
    }
    platformRepository.deleteById(id);
  }

  public List<PlatformResponse> mapToResponseList(List<Platform> genres) {
    return genres.stream()
        .map(this::mapToResponse)
        .toList();
  }

  public PlatformResponse mapToResponse(Platform platform) {
    return PlatformResponse.builder()
        .id(platform.getId())
        .name(platform.getName())
        .category(platform.getCategory())
        .slug(platform.getSlug())
        .build();
  }

}