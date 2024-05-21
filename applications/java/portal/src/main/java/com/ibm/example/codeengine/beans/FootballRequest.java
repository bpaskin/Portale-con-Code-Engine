package com.ibm.example.codeengine.beans;

import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FootballRequest implements Serializable {
    private String apiKey;
    private String leagueId;

    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public String getLeagueId() {
        return leagueId;
    }
    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }
}
