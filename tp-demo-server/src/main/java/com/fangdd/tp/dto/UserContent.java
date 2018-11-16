package com.fangdd.tp.dto;

import com.fangdd.tp.entity.Team;
import com.fangdd.tp.entity.User;

/**
 * @auth ycoe
 * @date 18/11/30
 */
public class UserContent {
    private User user;

    private Team team;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
