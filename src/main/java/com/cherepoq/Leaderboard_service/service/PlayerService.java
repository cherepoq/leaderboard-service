package com.cherepoq.Leaderboard_service.service;

import com.cherepoq.Leaderboard_service.Player;
import com.cherepoq.Leaderboard_service.dto.AddScoreRequest;
import com.cherepoq.Leaderboard_service.dto.CreatePlayerRequest;
import com.cherepoq.Leaderboard_service.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Player createPlayer(CreatePlayerRequest request) {
        String name = request.getName().trim();
        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name is required");
        }

        repository.findByName(name).ifPresent((p -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "player already exists");
        }));

        Player player = new Player(name);
        return repository.save(player);
    }

    public Player addScore(Long id, AddScoreRequest request) {
        Player player = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "player not found"));

        int newScore = player.getScore() + request.getDelta();
        player.setScore(Math.max(newScore, 0));
        return repository.save(player);
    }

    public List<Player> getLeaderboard(int limit) {
        return repository.findAll(
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "score"))
        ).getContent();
    }
}
