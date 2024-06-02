package com.example.Vapor.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "gamestats")
public class GameStats {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "last_played")
  private Date lastPlayed;
  @Column(name = "play_time_minutes")
  private Long playTimeMinutes;

  @ManyToOne
  @JoinColumn(name = "game_id")
  private Game game;

  @ManyToOne
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @Column(name = "rated")
  private boolean rated;

  public String getProfile() {
    return profile.getUsername();
  }


  public boolean isRated() {
    return rated;
  }

  public void setRated(boolean rated) {
    this.rated = rated;
  }

  public GameStats() {
    playTimeMinutes = 0L;
    rated = false;
  }

  public GameStats(Game game, Profile profile) {
    this.game = game;
    this.profile = profile;
    playTimeMinutes = 0L;
    rated = false;
  }

  public void rate() {
    rated = true;
  }

  public Date getLastPlayed() {
    return lastPlayed;
  }

  public void setLastPlayed(Date lastPlayed) {
    this.lastPlayed = lastPlayed;
  }

  public Long getPlayTimeMinutes() {
    return playTimeMinutes;
  }

  public void setPlayTimeMinutes(Long playTimeMinutes) {
    this.playTimeMinutes = playTimeMinutes;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

//    public Profile getProfile() {
//        return profile;
//    }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public void playFor(Long minutesPlayed) {
    playTimeMinutes += minutesPlayed;
    lastPlayed = new Date();
  }
}
