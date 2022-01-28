package io.balancer.elo.model;

import io.balancer.elo.service.PlayerServiceImplementation;
import lombok.Data;

import java.util.ArrayList;
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

        this.sumEloT1 = Math.round(sumElo1*100.0)/100.0;
        this.sumEloT2 = Math.round(sumElo2*100.0)/100.0;
        this.eloDifference = Math.round(abs(sumElo1 - sumElo2)*100.0)/100.0;
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
    public Team adjustRatings(int win){
        List<Player> ans1 = new ArrayList<>();
        List<Player> ans2 = new ArrayList<>();

        double elo1 = 0;
        double elo2 = 0;
        for(int i = 0; i < team1.size(); i++){
            team1.get(i).incrementStreak();
            log.info(String.valueOf(team1.get(i).getStreakBonus()));
            log.info(String.valueOf(1.0-win-calculateExpectedValueT1()));


            log.info("Updating Team 1 Player " + team1.get(i).getName() + "\'s elo(" + team1.get(i).getElo() + "): " +
                    String.valueOf(team1.get(i).getElo()+Math.round((team1.get(i).getStreakBonus()*kVal(team1.get(i).getElo())*(1-win-calculateExpectedValueT1()))*100.0)/100.0));
            team1.get(i).setElo(team1.get(i).getElo()
                    +Math.round((team1.get(i).getStreakBonus()*kVal(team1.get(i).getElo())
                    *(1-win-calculateExpectedValueT1()))
                    *100.0)/100.0);
            elo1 += team1.get(i).getElo();
            ans1.add(team1.get(i));
        }

        for(int i = 0; i < team2.size(); i++){
            team2.get(i).decrementStreak();
            log.info(String.valueOf(team2.get(i).getStreakBonus()));
            log.info(String.valueOf(0+win-calculateExpectedValueT2()));


            log.info("Updating Team 2 Player " + team2.get(i).getName() + "\'s elo(" + team2.get(i).getElo() + "): " +
                    String.valueOf(team2.get(i).getElo()+Math.round((team2.get(i).getStreakBonus()*kVal(team2.get(i).getElo())*(0+win-calculateExpectedValueT2()))*100.0)/100.0));
            team2.get(i).setElo(team2.get(i).getElo()
                    +Math.round((team2.get(i).getStreakBonus()*kVal(team2.get(i).getElo())
                    *(0+win-calculateExpectedValueT2()))
                    *100.0)/100.0);
            elo2 += team2.get(i).getElo();
            ans2.add(team2.get(i));
        }
        return new Team(ans1, ans2, elo1, elo2);
    }


}