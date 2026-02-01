package com.cherepoq.Leaderboard_service.controller;

import com.cherepoq.Leaderboard_service.Player;
import com.cherepoq.Leaderboard_service.repository.PlayerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerRepository repo;

    public PlayerController(PlayerRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Player> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Player create(@RequestBody CreatePlayerRequest request) {
        Player p = new Player();
        p.setName(request.name());
        p.setScore(0);
        return repo.save(p);
    }

    public record CreatePlayerRequest(String name) {}
}


