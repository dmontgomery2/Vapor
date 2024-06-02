package com.example.Vapor.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Entity(name = "profile")
public class Profile {

  @Id
  private Long id;

  @Column(name = "username")
  private String username;


  @OneToMany(mappedBy = "profile")
  private List<GameStats> gameStatsData;


  @ManyToMany
  @JoinTable(
      name = "friends",
      joinColumns = @JoinColumn(name = "requesterId"),
      inverseJoinColumns = @JoinColumn(name = "accepterId")
  )
  private Set<Profile> friends;

  @ManyToMany(mappedBy = "friends")
  private Set<Profile> reverseFriends;

  @ManyToMany
  @JoinTable(
      name = "pending_friends_sent",
      joinColumns = @JoinColumn(name = "requesterId"),
      inverseJoinColumns = @JoinColumn(name = "accepterId")
  )
  private Set<Profile> pendingFriendsSent;

  @ManyToMany(mappedBy = "pendingFriendsSent")
  private Set<Profile> reversePendingFriendsSent;

  @ManyToMany
  @JoinTable(
      name = "pending_friends_received",
      joinColumns = @JoinColumn(name = "accepterId"),
      inverseJoinColumns = @JoinColumn(name = "requesterId")
  )
  private Set<Profile> pendingFriendsReceived;

  @ManyToMany(mappedBy = "pendingFriendsReceived")
  private Set<Profile> reversePendingFriendsReceived;


  public void addFriend(Profile friend) {
    friends.add(friend);
  }

  public void removeFriend(Profile friend) {
    friends.remove(friend);
  }

  @ManyToMany
  @JoinTable(
      name = "trophies",
      joinColumns = @JoinColumn(name = "playerId"),
      inverseJoinColumns = @JoinColumn(name = "trophyId")
  )
  private List<Trophy> trophies;

  public List<String> getFriends() {
    return friends.stream().map(Profile::getUsername).collect(toList());
  }

  public void setFriends(Set<Profile> friends) {
    this.friends = friends;
  }

  public Set<String> getPendingFriendsSent() {
    return pendingFriendsSent.stream().map(Profile::getUsername).collect(toSet());
  }

  public void setPendingFriendsSent(Set<Profile> pendingFriendsSent) {
    this.pendingFriendsSent = pendingFriendsSent;
  }

  public Set<String> getPendingFriendsReceived() {
    return pendingFriendsReceived.stream()
        .map(Profile::getUsername)
        .collect(toSet());
  }

  public void setPendingFriendsReceived(Set<Profile> pendingFriendsReceived) {
    this.pendingFriendsReceived = pendingFriendsReceived;
  }

  public List<Trophy> getTrophies() {
    return trophies;
  }

  public void setTrophies(List<Trophy> trophies) {
    this.trophies = trophies;
  }

  public void earnTrophy(Trophy trophy) {
    trophies.add(trophy);
  }

  public void playGame(Game game, Long minutesPlayed) {
    gameStatsData.stream()
        .filter(gameStats -> gameStats.getGame().equals(game))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(username + " doesn't own " + game.getTitle()))
        .playFor(minutesPlayed);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<GameStats> getGameStatsData() {
    return gameStatsData;
  }

  public void setGameStatsData(List<GameStats> gameStatsData) {
    this.gameStatsData = gameStatsData;
  }


  public boolean ownsGame(Game game) {
    return gameStatsData.stream()
        .map(GameStats::getGame)
        .anyMatch(ownedGame -> ownedGame.getTitle().equals(game.getTitle()));
  }

  public boolean ownsTrophy(Trophy trophy) {
    return trophies.contains(trophy);
  }

  public void rateGame(Game game) {
    gameStatsData.stream()
        .filter(gameStats -> gameStats.getGame().equals(game))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "The profile with username " + username + " tried to rate a game they did not own."))
        .rate();
  }

  public boolean hasRatedGame(Game game) {
    return gameStatsData.stream()
        .filter(gameStats -> gameStats.getGame().equals(game))
        .findFirst()
        .orElse(new GameStats())
        .isRated();
  }

  public void receiveFriendRequest(Profile requester) {
    pendingFriendsReceived.add(requester);
  }

  public void sendFriendRequest(Profile accepter) {
    pendingFriendsSent.add(accepter);
  }

  public void cancelReceivedFriendRequest(Profile requester) {
    pendingFriendsReceived.remove(requester);
  }

  public void cancelSentFriendRequest(Profile accepter) {
    pendingFriendsSent.remove(accepter);
  }
}