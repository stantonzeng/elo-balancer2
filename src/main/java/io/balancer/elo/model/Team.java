package io.balancer.elo.model;

import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
public class Team{
    public Team(){

    }
    public Team(List<Player> t1, List<Player> t2, double sumElo1, double sumElo2){
        this.team1 = t1;
        this.team2 = t2;

        this.sumEloT1 = sumElo1;
        this.sumEloT2 = sumElo2;
    }

    private List<Player> team1;
    private List<Player> team2;

    private double sumEloT1;
    private double sumEloT2;

    private double eloDifference;


}