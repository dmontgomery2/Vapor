package com.example.Vapor.controllers;

import com.example.Vapor.models.Profile;
import com.example.Vapor.models.Trophy;
import com.example.Vapor.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.requireNonNull;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/profiles")

public class ProfileController {

  private final ProfileService profileService;

  public ProfileController(ProfileService profileService) {
    this.profileService = requireNonNull(profileService);
  }

  @GetMapping
  public List<Profile> getAll() {
    return profileService.getAll();
  }

  @GetMapping("/username/{username}")
  public Profile getProfileByUsername(@PathVariable String username) {
    return profileService.getProfileByUsername(username);
  }

  @GetMapping("/has_rated")
  public boolean profileHasRatedGame(@RequestParam("profileId") Long profileId,
      @RequestParam("gameId") Long gameId) {
    return profileService.profileHasRatedGame(profileId, gameId);
  }

  @GetMapping("/username/{username}/owns_game")
  public boolean profileOwnsGame(@PathVariable String username,
      @RequestParam("gameId") Long gameId) {
    return profileService.profileOwnsGame(username, gameId);
  }

  @GetMapping("/username/{username}/owns_trophy")
  public boolean profileOwnsTrophy(@PathVariable String username,
      @RequestParam("trophyId") Long trophyId) {
    return profileService.profileOwnsTrophy(username, trophyId);
  }

  @GetMapping("/username_containing")
  public List<Profile> getProfileByUsernameContainingEmpty() {
    return getAll();
  }

  @GetMapping("/username_containing/{usernameContains}")
  public List<Profile> getProfileByUsernameContaining(@PathVariable String usernameContains) {
    return profileService.getProfileByUsernameContaining(usernameContains);
  }

  @GetMapping("/username/{username}/trophies")
  public List<Trophy> getTrophiesForUsername(@PathVariable("username") String username) {
    return profileService.getTrophiesByUsername(username);
  }

  @GetMapping("/username/{username}/trophies/{gameTitle}")
  public List<Trophy> getTrophiesForUsernameAndGameTitle(@PathVariable("username") String username
      , @PathVariable("gameTitle") String gameTitle) {
    return profileService.getTrophiesByUsernameAndGameTitle(username, gameTitle);
  }

  @PutMapping("/friend_request_send")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void sendFriendRequest(@RequestParam("requesterId") Long requesterId,
      @RequestParam("accepterId") Long accepterId) {
    profileService.sendFriendRequest(requesterId, accepterId);
  }

  @PutMapping("/friend_request_cancel")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void cancelFriendRequest(@RequestParam("requesterId") Long requesterId,
      @RequestParam("accepterId") Long accepterId) {
    profileService.cancelFriendRequest(requesterId, accepterId);
  }

  @PutMapping("/friend_add")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createFriendship(@RequestParam("requesterId") Long requesterId,
      @RequestParam("accepterId") Long accepterId) {
    profileService.createFriendship(requesterId, accepterId);
    cancelFriendRequest(requesterId, accepterId);
  }

  @PutMapping("/friend_remove")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeFriendship(@RequestParam("activeRemoverId") Long activeRemoverId,
      @RequestParam("passiveRemoverId") Long passiveRemoverId) {
    profileService.removeFriendship(activeRemoverId, passiveRemoverId);
  }

  @GetMapping("/are_friends")
  public boolean areFriends(@RequestParam("profile_id_1") Long profileId1,
      @RequestParam("profile_id_2") Long profileId2) {
    return profileService.areFriends(profileId1, profileId2);
  }

//    const profileHasReceivedFriendRequestFromPrincipleData = (await axios.get(`http://localhost:5001/profiles/has_received_friend_request_from?receiver_id=${profileData.id}&sender_id=2`)).data;
//            setProfileHasReceivedFriendRequestFromPrincipal(profileHasReceivedFriendRequestFromPrincipleData);
//                const profileHasSentFriendRequestToPrincipleData = (await axios.get(`http://localhost:5001/profiles/has_sent_friend_request_to?sender_id=${profileData.id}&receiver_id=2`)).data;
//            setProfileHasSentFriendRequestToPrincipal(profileHasSentFriendRequestToPrincipleData);

  @GetMapping("/has_received_friend_request_from")
  public boolean hasReceivedFriendRequestFrom(@RequestParam("receiver_id") Long receiverId,
      @RequestParam("sender_id") Long senderId) {
    return profileService.hasReceivedFriendRequestFrom(receiverId, senderId);
  }

  @GetMapping("/has_sent_friend_request_to")
  public boolean hasSentFriendRequestTo(@RequestParam("sender_id") Long senderId,
      @RequestParam("receiver_id") Long receiverId) {
    return profileService.hasSentFriendRequestTo(senderId, receiverId);
  }


  @PutMapping("/trophy_earn")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void earnTrophy(@RequestParam("playerId") Long playerId,
      @RequestParam("trophyId") Long trophyId) {
    profileService.earnTrophy(playerId, trophyId);
  }

  @PutMapping("/game_played")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void playGame(@RequestParam("profileId") Long profileId,
      @RequestParam("gameId") Long gameId, @RequestParam("minutesPlayed") Long minutesPlayed) {
    profileService.playGame(profileId, gameId, minutesPlayed);
  }
}