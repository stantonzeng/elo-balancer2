package backend.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.abs;

@Data
@Slf4j
public class Team{
    public Team(){

    }
    public Team(List<Player> t1, List<Player> t2, double sumElo1, double sumElo2){
//        List<Player> newT1 = new ArrayList<>();
//        List<Player> newT2 = new ArrayList<>();
//        for(int i = 0; i < t1.size(); i++){
//            Player1 p1 = new Player1(t1.get(i));
//            newT1.add(p1);
//        }
//        for(int i = 0; i < t2.size(); i++){
//            Player2 p2 = new Player2(t2.get(i));
//            newT2.add(p2);
//        }
//        this.team1 = newT1;
//        this.team2 = newT2;
        //This is just to get around react-table's duplicate accessory problem. If I do ever solve that problem, then uncomment out the two lines below...
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
        StringBuilder t = new StringBuilder("[[");

        for (Player player : team1) {
            t.append(player.getName()).append(",");
        }
        t.append("]              [");

        for (Player player : team2) {
            t.append(player.getName()).append(",");
        }
        t.append("]");

        return t.toString();
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
        for (Player value : team1) {
            value.incrementStreak();
            log.info(String.valueOf(value.getStreakBonus()));
            log.info(String.valueOf((1.0 - win - calculateExpectedValueT1())));

            BigDecimal temp = BigDecimal.valueOf(kVal(value.getElo()) * ((1 - win - calculateExpectedValueT1()))).setScale(2, RoundingMode.HALF_UP);
            BigDecimal bd1 = BigDecimal.valueOf(value.getStreakBonus() * temp.doubleValue()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal val1 = BigDecimal.valueOf(value.getElo() + bd1.doubleValue()).setScale(2, RoundingMode.HALF_UP);


            log.info("Updating Team 1 Player " + value.getName() + "'s elo(" + value.getElo() + "): " +
                    String.valueOf(value.getElo() + bd1.doubleValue()));
            value.setElo(val1.doubleValue());
            elo1 += value.getElo();
            ans1.add(value);
        }

        for (Player player : team2) {
            player.decrementStreak();
            log.info(String.valueOf(player.getStreakBonus()));
            log.info(String.valueOf((win - calculateExpectedValueT2())));

            BigDecimal temp = BigDecimal.valueOf(kVal(player.getElo()) * ((win - calculateExpectedValueT2()))).setScale(2, RoundingMode.HALF_UP);
            BigDecimal bd2 = BigDecimal.valueOf(player.getStreakBonus() * temp.doubleValue()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal val2 = BigDecimal.valueOf(player.getElo() + bd2.doubleValue()).setScale(2, RoundingMode.HALF_UP);

            log.info("Updating Team 2 Player " + player.getName() + "'s elo(" + player.getElo() + "): " +
                    String.valueOf(val2.doubleValue()));
            player.setElo(val2.doubleValue());
            elo2 += player.getElo();
            ans2.add(player);
        }
        return new Team(ans1, ans2, elo1, elo2);
    }


}