package com.example.Vapor.services;

import com.example.Vapor.models.Game;
import com.example.Vapor.models.GameStats;
import com.example.Vapor.models.Profile;
import com.example.Vapor.models.Trophy;
import com.example.Vapor.repositories.GameRepository;
import com.example.Vapor.repositories.GameStatsRepository;
import com.example.Vapor.repositories.ProfileRepository;
import com.example.Vapor.repositories.TrophyRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class ProfileService {

  private final ProfileRepository profileRepository;
  private final GameRepository gameRepository;
  private final GameStatsRepository gameStatsRepository;
  private final TrophyRepository trophyRepository;

  public ProfileService(ProfileRepository profileRepository, GameRepository gameRepository,
      GameStatsRepository gameStatsRepository,
      TrophyRepository trophyRepository) {
    this.profileRepository = profileRepository;
    this.gameRepository = gameRepository;
    this.gameStatsRepository = gameStatsRepository;
    this.trophyRepository = trophyRepository;
  }

  public List<Profile> getAll() {
    return profileRepository.findAll();
  }

  public Profile getProfileByUsername(String username) {
    List<Profile> profiles = profileRepository.findAllByUsername(username);
    if (profiles.size() == 0) {
      throw new IllegalArgumentException("profile not found with username " + username);
    }
    if (profiles.size() > 1) {
      throw new IllegalStateException("more than one profile with username " + username);
    }
    return profiles.get(0);
  }

  public List<Trophy> getTrophiesByUsername(String username) {
    return getProfileByUsername(username).getTrophies();
  }

  private Game getGameByTitle(String title) {
    List<Game> games = gameRepository.findAllByTitle(title);
    if (games.size() == 0) {
      throw new IllegalArgumentException("game not found");
    }
    if (games.size() > 1) {
      throw new IllegalStateException("more than one game with same title");
    }
    return games.get(0);
  }

  public List<Trophy> getTrophiesByUsernameAndGameTitle(String username, String gameTitle) {
    Profile profile = getProfileByUsername(username);
    Game game = getGameByTitle(gameTitle);
    return trophyRepository.findAllByGame(game).stream()
        .filter(trophy -> trophy.getOwners().contains(profile))
        .collect(toList());
  }

  public void createFriendship(Long requesterId, Long accepterId) {
    Profile requester = profileRepository.findById(requesterId)
        .orElseThrow(() -> new IllegalArgumentException("requester not found"));
    Profile accepter = profileRepository.findById(accepterId)
        .orElseThrow(() -> new IllegalArgumentException("accepter not found"));
    requester.addFriend(accepter);
    accepter.addFriend(requester);
  }

  public void removeFriendship(Long activeRemoverId, Long passiveRemoverId) {
    Profile activeRemover = profileRepository.findById(activeRemoverId)
        .orElseThrow(() -> new IllegalArgumentException("activeRemover not found"));
    Profile passiveRemover = profileRepository.findById(passiveRemoverId)
        .orElseThrow(() -> new IllegalArgumentException("passiveRemover not found"));
    activeRemover.removeFriend(passiveRemover);
    passiveRemover.removeFriend(activeRemover);
  }

  public void sendFriendRequest(Long requesterId, Long accepterId) {
    Profile requester = profileRepository.getById(requesterId);
    Profile accepter = profileRepository.getById(accepterId);
    requester.sendFriendRequest(accepter);
    accepter.receiveFriendRequest(requester);
  }

  public void cancelFriendRequest(Long requesterId, Long accepterId) {
    Profile requester = profileRepository.getById(requesterId);
    Profile accepter = profileRepository.getById(accepterId);
    requester.cancelSentFriendRequest(accepter);
    accepter.cancelReceivedFriendRequest(requester);
  }

  public boolean areFriends(Long profileId1, Long profileId2) {
    Profile profile1 = profileRepository.getById(profileId1);
    Profile profile2 = profileRepository.getById(profileId2);
    return profile1.getFriends().contains(profile2.getUsername());
  }

  public boolean hasReceivedFriendRequestFrom(Long receiverId, Long senderId) {
    Profile receiver = profileRepository.getById(receiverId);
    Profile sender = profileRepository.getById(senderId);
    return receiver.getPendingFriendsReceived().contains(sender.getUsername());
  }

  public boolean hasSentFriendRequestTo(Long senderId, Long receiverId) {
    Profile sender = profileRepository.getById(senderId);
    Profile receiver = profileRepository.getById(receiverId);
    return sender.getPendingFriendsSent().contains(receiver.getUsername());
  }

  public void earnTrophy(Long playerId, Long trophyId) {
    Profile profile = profileRepository.findById(playerId)
        .orElseThrow(() -> new IllegalArgumentException("profile not found"));
    Trophy trophy = trophyRepository.findById(trophyId)
        .orElseThrow(() -> new IllegalArgumentException("trophy not found"));
    if (!profileOwnsGameForTrophy(profile, trophy)) {
      throw new IllegalArgumentException("profile does not own game for this trophy");
    }
    profile.earnTrophy(trophy);
  }

  private boolean profileOwnsGameForTrophy(Profile profile, Trophy trophy) {
    return profile
        .getGameStatsData()
        .stream()
        .map(GameStats::getGame)
        .anyMatch(game -> game.equals(trophy.getGame()));
  }


  public List<Profile> getProfileByUsernameContaining(String usernameContains) {
    return profileRepository.findByUsernameContains(usernameContains);
  }

  public boolean profileOwnsGame(String username, Long gameId) {
    Profile profile = getProfileByUsername(username);
    Game game = gameRepository.getById(gameId);
    return profile.ownsGame(game);
  }

  public boolean profileOwnsTrophy(String username, Long trophyId) {
    Profile profile = getProfileByUsername(username);
    Trophy trophy = trophyRepository.getById(trophyId);
    return profile.ownsTrophy(trophy);
  }

  public void playGame(Long profileId, Long gameId, Long minutesPlayed) {
    Profile profile = profileRepository.getById(profileId);
    Game game = gameRepository.getById(gameId);
    profile.playGame(game, minutesPlayed);
  }

  public boolean profileHasRatedGame(Long profileId, Long gameId) {
    Profile profile = profileRepository.getById(profileId);
    Game game = gameRepository.getById(gameId);
    return profile.hasRatedGame(game);
  }


}
