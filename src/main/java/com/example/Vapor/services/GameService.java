package com.example.Vapor.services;

import com.example.Vapor.models.Game;
import com.example.Vapor.models.Profile;
import com.example.Vapor.repositories.GameRepository;
import com.example.Vapor.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GameService {

  private final GameRepository gameRepository;
  private final ProfileRepository profileRepository;

  public GameService(GameRepository gameRepository, ProfileRepository profileRepository) {
    this.gameRepository = gameRepository;
    this.profileRepository = profileRepository;
  }

  public List<Game> getAll() {
    return gameRepository.findAll();
  }

  public Game getGameByTitle(String title) {
    List<Game> games = gameRepository.findAllByTitle(title);
      if (games.size() == 0) {
          throw new IllegalArgumentException("game not found");
      }
      if (games.size() > 1) {
          throw new IllegalStateException("more than one game with title: " + title);
      }
    return games.get(0);
  }

  public List<Game> getGameByTitleContaining(String titleContains) {
    return gameRepository.findAllByTitleContains(titleContains);
  }

  public void addRating(String title, Float rating, Long profileId) {
    Game game = getGameByTitle(title);
    game.addRating(rating);
    Profile profile = profileRepository.getById(profileId);
    profile.rateGame(game);
  }

  public void play(String title, Long profileId, Long minutesPlayed) {
    Game game = getGameByTitle(title);
    Profile profile = profileRepository.getById(profileId);
    profile.playGame(game, minutesPlayed);
  }
}
