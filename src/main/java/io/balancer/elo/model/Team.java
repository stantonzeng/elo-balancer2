package io.balancer.elo.model;

import lombok.Data;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Math.abs;

@Data
@Slf4j
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

//    public void changePlayerNameTest(String s){
//        this.team2.get(4).setName(s);
//    }

    public double calculateExpectedValueT1(){
        return 1/(1+Math.pow(10, (this.sumEloT2 - this.sumEloT1)/(400*(this.team1.size()+this.team2.size()))));
    }

    public double calculateExpectedValueT2(){
        return 1/(1+Math.pow(10, (this.sumEloT1 - this.sumEloT2)/(400*(this.team1.size()+this.team2.size()))));
    }

    //Theres a more mathy way of doing this, but I'm too tired and will probably do this later...
    //FIX ME(but later)
    public int kVal(double elo){
        if(elo > 2200.0) return 16;
        else if(elo > 2000) return 20;
        else if(elo > 1800) return 24;
        else if(elo > 1600) return 22;
        else if(elo > 1400) return 28;
        else return 32;
    }

    //If win == 0, then team1 won, else if win == 1, then team2 won
    public void adjustRatings(int win){
        for(int i = 0; i < team1.size(); i++){
            log.info("Updating Team 1 Player " + team1.get(i).getName() + "\'s elo(" + team1.get(i).getElo() + "): " +
                    String.valueOf(team1.get(i).getElo()+Math.round((kVal(team1.get(i).getElo())*(1-win-calculateExpectedValueT1()))*100.0)/100.0));
            team1.get(i).setElo(team1.get(i).getElo()
                    +Math.round((kVal(team1.get(i).getElo())
                    *(1-win-calculateExpectedValueT1()))
                    *100.0)/100.0);
        }

        for(int i = 0; i < team2.size(); i++){
            log.info("Updating Team 2 Player " + team2.get(i).getName() + "\'s elo(" + team2.get(i).getElo() + "): " +
                    String.valueOf(team2.get(i).getElo()+Math.round((kVal(team2.get(i).getElo())*(0+win-calculateExpectedValueT2()))*100.0)/100.0));
            team2.get(i).setElo(team2.get(i).getElo()
                    +Math.round((kVal(team2.get(i).getElo())
                    *(0+win-calculateExpectedValueT2()))
                    *100.0)/100.0);
        }
    }


}