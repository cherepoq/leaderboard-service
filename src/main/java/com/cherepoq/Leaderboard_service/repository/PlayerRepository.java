package com.cherepoq.Leaderboard_service.repository;

import com.cherepoq.Leaderboard_service.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
