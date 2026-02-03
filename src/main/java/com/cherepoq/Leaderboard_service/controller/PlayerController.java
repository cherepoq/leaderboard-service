package com.cherepoq.Leaderboard_service.controller;

import com.cherepoq.Leaderboard_service.Player;
import com.cherepoq.Leaderboard_service.dto.AddScoreRequest;
import com.cherepoq.Leaderboard_service.dto.CreatePlayerRequest;
import com.cherepoq.Leaderboard_service.dto.PlayerResponse;
import com.cherepoq.Leaderboard_service.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @PostMapping
    public PlayerResponse create(@RequestBody CreatePlayerRequest request) {
        Player p = service.createPlayer(request);
        return toResponse(p);
    }

    // 2) Добавить очки игроку
    @PostMapping("/{id}/score")
    public PlayerResponse addScore(
        @PathVariable Long id,
        @RequestBody AddScoreRequest request
    ) {
        Player p = service.addScore(id, request);
        return toResponse(p);

    }

    // 3) Топ игроков
    @GetMapping("/leaderboard")
    public List<PlayerResponse> leaderboard(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return service.getLeaderboard(limit)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    private PlayerResponse toResponse(Player p) {
        return new PlayerResponse(p.getId(), p.getName(), p.getScore(), p.getCreatedAt());
    }
}
