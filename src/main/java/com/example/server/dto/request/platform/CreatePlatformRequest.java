package com.example.server.dto.request.platform;

import lombok.Data;

@Data
public class CreatePlatformRequest {
  private String name;
  private String category;
}