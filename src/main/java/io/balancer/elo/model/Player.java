package io.balancer.elo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*; //Comes from Java Persistence API (JPA), Maps Java objects to database tables using
//Object-relational mapping (ORM), Spring data JPA allows for connection to database
import javax.validation.constraints.NotEmpty;

@Entity //Turns our class into an entity so we can map it to db
@NoArgsConstructor
// @AllArgsConstructor
public class Player {
    public Player(){
        
    }

    public Player(Long idVal, Double eloVal, String nameVal, double streakVal){
        this.id = idVal;
        this.elo = eloVal;
        this.name = nameVal;
        this.streak = streakVal;
    }

    @Id //Identifies the primary key for the @Entity value
    @GeneratedValue(strategy = GenerationType.AUTO) //HOW we generate our Id's for players
    private Long id;
    private Double elo;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private double streak;

    public Long getId() {
        return this.id;
    }
    public void setId(Long val){
        this.id = val;
    }
    public double getElo(){
        return this.elo;
    }
    public void setElo(Double val){
        this.elo = val;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String nam){
        this.name = nam;
    }
    public double getStreak(){
        return this.streak;
    }
    public void setStreak(double val){
        this.streak = val;
    }

    public void incrementStreak(){
        this.streak += 1.0;
    }
    public void decrementStreak(){
        this.streak -= 1.0;
    }

    public double getStreakBonus(){
        return 1.0+(Math.round(Math.pow(Math.abs(this.streak/12.0),2)*1000.0)/1000.0);
    }

}
