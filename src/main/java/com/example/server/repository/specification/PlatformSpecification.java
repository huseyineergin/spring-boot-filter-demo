package com.example.server.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.server.entity.Platform;

public class PlatformSpecification {

  public static Specification<Platform> hasCategory(String category) {
    return (root, query, cb) -> {
      if (category == null) {
        return cb.conjunction();
      }

      return cb.equal(root.get("category"), category);
    };
  }

}