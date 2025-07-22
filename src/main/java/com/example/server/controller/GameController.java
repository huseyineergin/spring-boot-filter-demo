package com.example.server.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.request.filter.GameFilterRequest;
import com.example.server.dto.request.game.CreateGameRequest;
import com.example.server.dto.request.game.UpdateGameRequest;
import com.example.server.dto.response.GameResponse;
import com.example.server.service.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/games")
public class GameController {

  private final GameService gameService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<GameResponse> createGame(@ModelAttribute CreateGameRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(gameService.mapToResponse(gameService.createGame(request)));
  }

  @GetMapping
  public ResponseEntity<List<GameResponse>> getAllGames(@ModelAttribute GameFilterRequest filters) {
    return ResponseEntity.ok(gameService.mapToResponseList(gameService.getAllGames(filters)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GameResponse> getGameById(@PathVariable UUID id) {
    return ResponseEntity.ok(gameService.mapToResponse(gameService.getGameById(id)));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<GameResponse> updateGameById(@PathVariable UUID id, @ModelAttribute UpdateGameRequest request) {
    return ResponseEntity.ok(gameService.mapToResponse(gameService.updateGameById(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteGameById(@PathVariable UUID id) {
    gameService.deleteGameById(id);
    return ResponseEntity.noContent().build();
  }

}