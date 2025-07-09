package com.example.server.utils;

public class SlugUtils {

  public static String generateSlug(String input) {
    return input.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
  }

}
