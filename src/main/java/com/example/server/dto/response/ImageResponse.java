package com.example.server.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponse {
  private UUID id;
  private String type;
}