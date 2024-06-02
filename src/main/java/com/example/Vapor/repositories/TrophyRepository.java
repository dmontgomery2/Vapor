package com.example.Vapor.repositories;

import com.example.Vapor.models.Game;
import com.example.Vapor.models.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrophyRepository extends JpaRepository<Trophy, Long> {

  List<Trophy> findAllByGame(Game game);
}
