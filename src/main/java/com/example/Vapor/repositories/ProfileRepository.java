package com.example.Vapor.repositories;

import com.example.Vapor.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

  List<Profile> findAllByUsername(String username);

  @Query("select p from profile as p where p.username like %:usernameContains%")
  List<Profile> findByUsernameContains(@Param("usernameContains") String usernameContains);
}
