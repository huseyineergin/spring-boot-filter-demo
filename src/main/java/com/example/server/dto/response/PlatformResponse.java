package com.example.server.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlatformResponse {
  private UUID id;
  private String name;
  private String category;
  private String slug;
}