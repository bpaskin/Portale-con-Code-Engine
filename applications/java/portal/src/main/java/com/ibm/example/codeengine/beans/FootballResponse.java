package com.ibm.example.codeengine.beans;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FootballResponse implements Serializable {
    private String league;
    private List<FootballStanding> standings;
    
    public String getLeague() {
        return league;
    }
    public void setLeague(String league) {
        this.league = league;
    }
    public List<FootballStanding> getStandings() {
        return standings;
    }
    public void setStandings(List<FootballStanding> standings) {
        this.standings = standings;
    }

}
