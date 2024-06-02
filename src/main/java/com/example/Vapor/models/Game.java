package com.example.Vapor.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "game")
public class Game {

  @Id
  private Long id;
  @Column(name = "title")
  private String title;
  @Column(name = "released")
  private Date released;
  @Column(name = "price")
  private Long price;
  @Column(name = "publisher")
  private String publisher;
  @Column(name = "developer")
  private String developer;
  @Column(name = "genre")
  private String genre;
  @Column(name = "description")
  private String description;
  @Column(name = "file_size")
  private Float fileSize;
  @Column(name = "rating")
  private Float avgRating;
  @JsonIgnore
  @Column(name = "num_ratings")
  private Long numRatings;

  @OneToMany(mappedBy = "game")
  private List<Trophy> trophies;

  @OneToMany(mappedBy = "game")
  private List<GameStats> gameStatsData;

  public Date getReleased() {
    return released;
  }

  public void setReleased(Date released) {
    this.released = released;
  }

  public Float getAvgRating() {
    return avgRating;
  }

  public void setAvgRating(Float avgRating) {
    this.avgRating = avgRating;
  }

  public Long getNumRatings() {
    return numRatings;
  }

  public void setNumRatings(Long numRatings) {
    this.numRatings = numRatings;
  }

  public Long getPrice() {
    return price;
  }

  public List<Trophy> getTrophies() {
    return trophies;
  }

  public void setTrophies(List<Trophy> trophies) {
    this.trophies = trophies;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public Float getFileSize() {
    return fileSize;
  }

  public void setFileSize(Float fileSize) {
    this.fileSize = fileSize;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getDeveloper() {
    return developer;
  }

  public void setDeveloper(String developer) {
    this.developer = developer;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void addRating(Float rating) {
    float numRatingsAsFloat = Float.parseFloat(Long.toString(numRatings));
    if (numRatings == 0) {
      avgRating = rating;
    } else {
      avgRating =
          avgRating * (numRatingsAsFloat / (numRatingsAsFloat + 1)) + (rating / (numRatingsAsFloat
              + 1));
    }
    numRatings++;
  }
}
