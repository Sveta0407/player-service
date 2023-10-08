package com.intuit.playerservice.repository;

import com.intuit.playerservice.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    Optional<Player> findPlayerByPlayerId(String playerId);

}
