package com.example.server.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.request.filter.PlatformFilterRequest;
import com.example.server.dto.request.genre.UpdateGenreRequest;
import com.example.server.dto.request.platform.CreatePlatformRequest;
import com.example.server.dto.response.PlatformResponse;
import com.example.server.service.PlatformService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/platforms")
public class PlatformController {

  private final PlatformService platformService;

  @PostMapping
  public ResponseEntity<PlatformResponse> createPlatform(@RequestBody CreatePlatformRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(platformService.mapToResponse(platformService.createPlatform(request)));
  }

  @GetMapping
  public ResponseEntity<List<PlatformResponse>> getAllGenres(@ModelAttribute PlatformFilterRequest filters) {
    return ResponseEntity.ok(platformService.mapToResponseList(platformService.getAllPlatforms(filters)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PlatformResponse> getGenreById(@PathVariable UUID id) {
    return ResponseEntity.ok(platformService.mapToResponse(platformService.getPlatformById(id)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PlatformResponse> updatePlatformById(
      @PathVariable UUID id,
      @RequestBody UpdateGenreRequest request) {
    return ResponseEntity.ok(platformService.mapToResponse(platformService.updatePlatformById(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePlatformById(@PathVariable UUID id) {
    platformService.deletePlatformById(id);
    return ResponseEntity.noContent().build();
  }

}
