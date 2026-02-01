package com.cherepoq.Leaderboard_service;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false)
    private Integer score = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Player() {}

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (score == null) score = 0;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getScore() { return score; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setName(String name) { this.name = name; }
    public void setScore(Integer score) { this.score = score; }
}
