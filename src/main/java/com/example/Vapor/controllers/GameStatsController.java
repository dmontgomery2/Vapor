package com.example.Vapor.controllers;

import com.example.Vapor.models.Game;
import com.example.Vapor.models.GameStats;
import com.example.Vapor.services.GameService;
import com.example.Vapor.services.GameStatsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.requireNonNull;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/game_stats")

public class GameStatsController {

  private final GameStatsService gameStatsService;

  public GameStatsController(GameStatsService gameService) {
    this.gameStatsService = requireNonNull(gameService);
  }

  @PostMapping("/purchase")
  public synchronized void postGamePurchase(@RequestParam("gameId") Long gameId,
      @RequestParam("profileId") Long profileId) {
    gameStatsService.postGameStats(gameId, profileId);
  }

}
