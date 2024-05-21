package com.ibm.example.codeengine.beans;

import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FootballStanding implements Serializable {
    private String rank;
    private String name;
    private String point;
    private String gamesPlayed;
    private String gamesWon;
    private String gamesLost;
    private String gamesDraw;
    
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPoint() {
        return point;
    }
    public void setPoint(String point) {
        this.point = point;
    }
    public String getGamesPlayed() {
        return gamesPlayed;
    }
    public void setGamesPlayed(String gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
    public String getGamesWon() {
        return gamesWon;
    }
    public void setGamesWon(String gamesWon) {
        this.gamesWon = gamesWon;
    }
    public String getGamesLost() {
        return gamesLost;
    }
    public void setGamesLost(String gamesLost) {
        this.gamesLost = gamesLost;
    }
    public String getGamesDraw() {
        return gamesDraw;
    }
    public void setGamesDraw(String gamesDraw) {
        this.gamesDraw = gamesDraw;
    }

}
