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

import com.example.server.dto.request.filter.GenreFilterRequest;
import com.example.server.dto.request.genre.CreateGenreRequest;
import com.example.server.dto.request.genre.UpdateGenreRequest;
import com.example.server.dto.response.GenreResponse;
import com.example.server.service.GenreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
public class GenreController {

  private final GenreService genreService;

  @PostMapping
  public ResponseEntity<GenreResponse> createGenre(@RequestBody CreateGenreRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(genreService.mapToResponse(genreService.createGenre(request)));
  }

  @GetMapping
  public ResponseEntity<List<GenreResponse>> getAllGenres(@ModelAttribute GenreFilterRequest filters) {
    return ResponseEntity.ok(genreService.mapToResponseList(genreService.getAllGenres(filters)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenreResponse> getGenreById(@PathVariable UUID id) {
    return ResponseEntity.ok(genreService.mapToResponse(genreService.getGenreById(id)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenreResponse> updateGenreById(@PathVariable UUID id, @RequestBody UpdateGenreRequest request) {
    return ResponseEntity.ok(genreService.mapToResponse(genreService.updateGenreById(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteGenreById(@PathVariable UUID id) {
    genreService.deleteGenreById(id);
    return ResponseEntity.noContent().build();
  }

}
