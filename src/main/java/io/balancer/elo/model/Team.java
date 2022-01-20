package io.balancer.elo.model;

import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Math.abs;

@Data
public class Team{
    public Team(){

    }
    public Team(List<Player> t1, List<Player> t2, double sumElo1, double sumElo2){
        this.team1 = t1;
        this.team2 = t2;

        this.sumEloT1 = sumElo1;
        this.sumEloT2 = sumElo2;
        this.eloDifference = abs(sumElo1 - sumElo2);
    }

    private List<Player> team1;
    private List<Player> team2;

    private double sumEloT1;
    private double sumEloT2;

    private double eloDifference;

    public String printAllPlayers(){
        String t = "[[";

        for(int i = 0; i <team1.size(); i++){
            t = t + team1.get(i).getName() + ",";
        }
        t = t + "]              [";

        for(int i = 0; i <team2.size(); i++){
            t = t + team2.get(i).getName() + ",";
        }
        t = t + "]";

        return t;
    }

    public void changePlayerNameTest(String s){
        this.team2.get(4).setName(s);
    }


}