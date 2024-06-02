package com.example.Vapor.controllers;

import com.example.Vapor.models.Game;
import com.example.Vapor.services.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.requireNonNull;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/games")

public class GameController {

  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = requireNonNull(gameService);
  }

  @GetMapping
  public List<Game> getAll() {
    return gameService.getAll();
  }


  @GetMapping("/title/{title}")
  public Game getGameByTitle(@PathVariable String title) {
    return gameService.getGameByTitle(title);
  }

  @PutMapping("/title/{title}/rate")
  public void addRating(@PathVariable String title, @RequestParam("rating") Float rating,
      @RequestParam("profileId") Long profileId) {
    gameService.addRating(title, rating, profileId);
  }

  @PutMapping("/title/{title}/play")
  public void play(@PathVariable String title, @RequestParam("profileId") Long profileId,
      @RequestParam("minutesPlayed") Long minutesPlayed) {
    gameService.play(title, profileId, minutesPlayed);
  }

  @GetMapping("/title_containing")
  public List<Game> getGameByTitleContainingEmpty() {
    return getAll();
  }

  @GetMapping("/title_containing/{titleContains}")
  public List<Game> getGameByTitleContaining(@PathVariable String titleContains) {
    return gameService.getGameByTitleContaining(titleContains);
  }


}
