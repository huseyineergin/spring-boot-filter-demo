package com.example.server.dto.request.game;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CreateGameRequest {
  private String name;
  private String description;
  private LocalDate releaseDate;
  private Set<UUID> platformIds;
  private Double metascore;
  private Double userScore;
  private String developer;
  private String publisher;
  private Set<UUID> genreIds;
  private MultipartFile headerImage;
}