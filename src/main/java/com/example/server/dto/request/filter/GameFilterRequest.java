package com.example.server.dto.request.filter;

import java.util.List;

import lombok.Data;

@Data
public class GameFilterRequest {
  private List<String> genres;
  private List<String> platforms;
  private Double minMetascore;
  private Double maxMetascore;
  private Double minUserScore;
  private Double maxUserScore;
  private Integer minYear;
  private Integer maxYear;
}