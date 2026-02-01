package com.cherepoq.Leaderboard_service.dto;

import java.time.LocalDateTime;

public class PlayerResponse {
    private Long id;
    private String name;
    private Integer score;
    private LocalDateTime createdAt;

    public PlayerResponse() {}

    public PlayerResponse(Long id, String name, Integer score, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getName() {return name;}
    public Integer getScore() {return score;}
    public LocalDateTime getCreatedAt() {return createdAt;}
}
