package com.example.Vapor.services;

import com.example.Vapor.models.Game;
import com.example.Vapor.models.GameStats;
import com.example.Vapor.models.Profile;
import com.example.Vapor.repositories.GameRepository;
import com.example.Vapor.repositories.GameStatsRepository;
import com.example.Vapor.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GameStatsService {

  private final GameStatsRepository gameStatsRepository;
  private final GameRepository gameRepository;
  private final ProfileRepository profileRepository;

  public GameStatsService(GameStatsRepository gameStatsRepository, GameRepository gameRepository,
      ProfileRepository profileRepository) {
    this.gameStatsRepository = gameStatsRepository;
    this.gameRepository = gameRepository;
    this.profileRepository = profileRepository;
  }

  public synchronized void postGameStats(Long gameId, Long profileId) {
    Game game = gameRepository.getById(gameId);
    Profile profile = profileRepository.getById(profileId);
      if (profile.ownsGame(game)) {
          return;
      }
    gameStatsRepository.save(new GameStats(game, profile));
  }
}

