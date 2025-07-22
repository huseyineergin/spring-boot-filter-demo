package com.example.server.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.server.dto.request.filter.GenreFilterRequest;
import com.example.server.dto.request.genre.CreateGenreRequest;
import com.example.server.dto.request.genre.UpdateGenreRequest;
import com.example.server.dto.response.GenreResponse;
import com.example.server.entity.Genre;
import com.example.server.repository.GenreRepository;
import com.example.server.repository.specification.GenreSpecification;
import com.example.server.utils.SlugUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreService {

  private final GenreRepository genreRepository;

  public Genre createGenre(CreateGenreRequest request) {
    Genre genre = Genre.builder()
        .name(request.getName())
        .category(request.getCategory())
        .slug(SlugUtils.generateSlug(request.getName()))
        .build();
    return genreRepository.save(genre);
  }

  public List<Genre> getAllGenres(GenreFilterRequest filters) {
    Specification<Genre> spec = GenreSpecification.hasCategory(filters.getCategory());

    return genreRepository.findAll(spec);
  }

  public Genre getGenreById(UUID id) {
    return genreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Genre not found."));
  }

  public Genre updateGenreById(UUID id, UpdateGenreRequest request) {
    Genre existingGenre = getGenreById(id);

    existingGenre.setName(request.getName());
    existingGenre.setCategory(request.getCategory());

    return genreRepository.save(existingGenre);
  }

  public void deleteGenreById(UUID id) {
    if (!genreRepository.existsById(id)) {
      throw new EntityNotFoundException("Genre not found.");
    }
    genreRepository.deleteById(id);
  }

  public List<GenreResponse> mapToResponseList(List<Genre> genres) {
    return genres.stream()
        .map(this::mapToResponse)
        .toList();
  }

  public GenreResponse mapToResponse(Genre genre) {
    return GenreResponse.builder()
        .id(genre.getId())
        .name(genre.getName())
        .category(genre.getCategory())
        .slug(genre.getSlug())
        .build();
  }

}