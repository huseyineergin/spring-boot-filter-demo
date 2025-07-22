package com.example.server.dto.request.genre;

import lombok.Data;

@Data
public class CreateGenreRequest {
  private String name;
  private String category;
}