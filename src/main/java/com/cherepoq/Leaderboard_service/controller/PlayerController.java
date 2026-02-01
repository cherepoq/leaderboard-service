package com.cherepoq.Leaderboard_service.controller;

import com.cherepoq.Leaderboard_service.Player;
import com.cherepoq.Leaderboard_service.dto.AddScoreRequest;
import com.cherepoq.Leaderboard_service.dto.CreatePlayerRequest;
import com.cherepoq.Leaderboard_service.dto.PlayerResponse;
import com.cherepoq.Leaderboard_service.repository.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@RestController
public class PlayerController {

    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // 1) Создать игрока
    @PostMapping("/players")
    public PlayerResponse create(@RequestBody CreatePlayerRequest request) {
        String name = (request.getName() == null) ? "" : request.getName().trim();
        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name is required");
        }
        if (name.length() > 64) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name max length is 64");
        }

        // опционально: запретим дубликаты по имени
        playerRepository.findByName(name).ifPresent(p -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "player with this name already exists");
        });

        Player player = new Player(name);
        Player saved = playerRepository.save(player);

        return toResponse(saved);
    }

    // 2) Добавить очки игроку
    @PostMapping("/players/{id}/score")
    public PlayerResponse addScore(@PathVariable Long id, @RequestBody AddScoreRequest request) {
        Integer delta = request.getDelta();
        if (delta == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "delta is required");
        }

        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "player not found"));

        int newScore = player.getScore() + delta;
        if (newScore < 0) newScore = 0; // чтобы не уходило в минус

        player.setScore(newScore);
        Player saved = playerRepository.save(player);

        return toResponse(saved);
    }

    // 3) Топ игроков
    @GetMapping("/leaderboard")
    public List<PlayerResponse> leaderboard(@RequestParam(defaultValue = "10") int limit) {
        if (limit < 1) limit = 1;
        if (limit > 100) limit = 100;

        // простой вариант: берём всех и сортируем (ок для дня 2)
        // позже улучшим: запросом "top N" через JPA / pageable
        List<Player> all = playerRepository.findAll();
        return all.stream()
                .sorted(Comparator.comparing(Player::getScore).reversed())
                .limit(limit)
                .map(this::toResponse)
                .toList();
    }

    private PlayerResponse toResponse(Player p) {
        return new PlayerResponse(p.getId(), p.getName(), p.getScore(), p.getCreatedAt());
    }
}
