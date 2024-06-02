package com.example.Vapor.repositories;

import com.example.Vapor.models.GameStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameStatsRepository extends JpaRepository<GameStats, Long> {

}
