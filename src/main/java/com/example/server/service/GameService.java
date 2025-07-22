package com.example.server.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.server.dto.request.filter.GameFilterRequest;
import com.example.server.dto.request.game.CreateGameRequest;
import com.example.server.dto.request.game.UpdateGameRequest;
import com.example.server.dto.response.GameResponse;
import com.example.server.dto.response.ImageResponse;
import com.example.server.entity.Game;
import com.example.server.entity.Genre;
import com.example.server.entity.Image;
import com.example.server.entity.Platform;
import com.example.server.repository.GameRepository;
import com.example.server.repository.GenreRepository;
import com.example.server.repository.PlatformRepository;
import com.example.server.repository.specification.GameSpecification;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

  private final GameRepository gameRepository;
  private final GenreRepository genreRepository;
  private final PlatformRepository platformRepository;

  public Game createGame(CreateGameRequest request) {
    Image image = null;
    MultipartFile file = request.getHeaderImage();
    if (file != null && !file.isEmpty()) {
      try {
        image = Image.builder()
            .type(file.getContentType())
            .data(file.getBytes())
            .build();
      } catch (IOException e) {
        throw new RuntimeException("Failed to process uploaded image.", e); // TODO
      }
    }

    Game game = Game.builder()
        .name(request.getName())
        .description(request.getDescription())
        .releaseDate(request.getReleaseDate())
        .metascore(request.getMetascore())
        .userScore(request.getUserScore())
        .developer(request.getDeveloper())
        .publisher(request.getPublisher())
        .platforms(new HashSet<>(platformRepository.findAllById(request.getPlatformIds())))
        .genres(new HashSet<>(genreRepository.findAllById(request.getGenreIds())))
        .headerImage(image)
        .build();

    return gameRepository.save(game);
  }

  public List<Game> getAllGames(GameFilterRequest filters) {
    Specification<Game> spec = GameSpecification.hasGenres(filters.getGenres())
        .and(GameSpecification.hasPlatforms(filters.getPlatforms()))
        .and(GameSpecification.hasMetascoreBetween(filters.getMinMetascore(), filters.getMaxMetascore()))
        .and(GameSpecification.hasUserScoreBetween(filters.getMinUserScore(), filters.getMaxUserScore()))
        .and(GameSpecification.hasReleaseYearBetween(filters.getMinYear(), filters.getMaxYear()));

    return gameRepository.findAll(spec);
  }

  public Game getGameById(UUID id) {
    return gameRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Game not found."));
  }

  public Game updateGameById(UUID id, UpdateGameRequest request) {
    Game existingGame = getGameById(id);

    MultipartFile file = request.getHeaderImage();
    if (file != null && !file.isEmpty()) {
      try {
        Image image = Image.builder()
            .type(file.getContentType())
            .data(file.getBytes())
            .build();
        existingGame.setHeaderImage(image);
      } catch (IOException e) {
        throw new RuntimeException("Failed to process uploaded image.", e); // TODO
      }
    }

    existingGame.setName(request.getName());
    existingGame.setDescription(request.getDescription());
    existingGame.setReleaseDate(request.getReleaseDate());
    existingGame.setMetascore(request.getMetascore());
    existingGame.setUserScore(request.getUserScore());
    existingGame.setDeveloper(request.getDeveloper());
    existingGame.setPublisher(request.getPublisher());
    existingGame.setGenres(new HashSet<>(genreRepository.findAllById(request.getGenreIds())));
    existingGame.setPlatforms(new HashSet<>(platformRepository.findAllById(request.getPlatformIds())));

    return gameRepository.save(existingGame);
  }

  public void deleteGameById(UUID id) {
    if (!gameRepository.existsById(id)) {
      throw new EntityNotFoundException("Game not found.");
    }
    gameRepository.deleteById(id);
  }

  public List<GameResponse> mapToResponseList(List<Game> games) {
    return games.stream()
        .map(this::mapToResponse)
        .toList();
  }

  public GameResponse mapToResponse(Game game) {
    return GameResponse.builder()
        .id(game.getId())
        .name(game.getName())
        .description(game.getDescription())
        .releaseDate(game.getReleaseDate())
        .metascore(game.getMetascore())
        .userScore(game.getUserScore())
        .developer(game.getDeveloper())
        .publisher(game.getPublisher())
        .platforms(game.getPlatforms().stream().map(Platform::getName).toList())
        .genres(game.getGenres().stream().map(Genre::getName).toList())
        .headerImage(
            game.getHeaderImage() != null
                ? ImageResponse.builder()
                    .id(game.getHeaderImage().getId())
                    .type(game.getHeaderImage().getType())
                    .build()
                : null)
        .build();
  }

}