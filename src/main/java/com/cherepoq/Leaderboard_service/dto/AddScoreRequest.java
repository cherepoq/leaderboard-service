package com.cherepoq.Leaderboard_service.dto;

public class AddScoreRequest {
    private Integer delta;

    public AddScoreRequest() {}

    public Integer getDelta() {
        return delta;
    }
    public void setDelta(Integer delta) {
        this.delta = delta;
    }
}
