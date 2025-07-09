package com.example.server.repository.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.server.entity.Game;
import com.example.server.entity.Genre;
import com.example.server.entity.Platform;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class GameSpecification {

  public static Specification<Game> hasGenres(List<String> genres) {
    return (root, query, cb) -> {
      if (genres == null || genres.isEmpty()) {
        return cb.conjunction();
      }

      Join<Game, Genre> genreJoin = root.join("genres");
      return cb.and(
          genreJoin.get("slug").in(genres),
          cb.equal(genreJoin.get("category"), "game") //
      );
    };
  }

  public static Specification<Game> hasPlatforms(List<String> platforms) {
    return (root, query, cb) -> {
      if (platforms == null || platforms.isEmpty()) {
        return cb.conjunction();
      }

      Join<Game, Platform> platformJoin = root.join("platforms");
      return cb.and(
          platformJoin.get("slug").in(platforms),
          cb.equal(platformJoin.get("category"), "game") //
      );
    };
  }

  public static Specification<Game> hasMetascoreBetween(Double min, Double max) {
    return (root, query, cb) -> {
      Predicate predicate = cb.conjunction();

      if (min != null) {
        predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("metascore"), min));
      }
      if (max != null) {
        predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("metascore"), max));
      }

      return predicate;
    };
  }

  public static Specification<Game> hasUserScoreBetween(Double min, Double max) {
    return (root, query, cb) -> {
      Predicate predicate = cb.conjunction();

      if (min != null) {
        predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("userScore"), min));
      }
      if (max != null) {
        predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("userScore"), max));
      }

      return predicate;
    };
  }

  public static Specification<Game> hasReleaseYearBetween(Integer minYear, Integer maxYear) {
    return (root, query, cb) -> {
      Expression<Integer> releaseYear = cb.function("year", Integer.class, root.get("releaseDate"));

      Predicate predicate = cb.conjunction();

      if (minYear != null) {
        predicate = cb.and(predicate, cb.greaterThanOrEqualTo(releaseYear, minYear));
      }
      if (maxYear != null) {
        predicate = cb.and(predicate, cb.lessThanOrEqualTo(releaseYear, maxYear));
      }

      return predicate;
    };
  }

}