package com.example.Vapor.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "trophy")
public class Trophy {

  @Id
  private Long id;
  @Column(name = "title")
  private String title;
  @Column(name = "description")
  private String description;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "gameId")
  private Game game;

  @ManyToMany(mappedBy = "trophies")
  @JsonIgnore
  private List<Profile> owners;

//    public float getPercentAchieved(){
//        return owners.size() / game.getOwners().size();
//    }

  public String getGameTitle() {
    return game.getTitle();
  }

  public List<Profile> getOwners() {
    return owners;
  }

  public void setOwners(List<Profile> owners) {
    this.owners = owners;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return title;
  }
}
