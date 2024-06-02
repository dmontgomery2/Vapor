package com.example.Vapor.repositories;

import com.example.Vapor.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

  List<Game> findAllByTitle(String title);

  @Query("select g from game as g where g.title like %:titleContains%")
  List<Game> findAllByTitleContains(@Param("titleContains") String titleContains);
}
