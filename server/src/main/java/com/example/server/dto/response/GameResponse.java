package com.example.server.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResponse {
  private UUID id;
  private String name;
  private String description;
  private LocalDate releaseDate;
  private List<String> platforms;
  private List<String> genres;
  private Double metascore;
  private Double userScore;
  private String developer;
  private String publisher;
  private ImageResponse headerImage;
}