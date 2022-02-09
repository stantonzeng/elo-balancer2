package io.balancer.elo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*; //Comes from Java Persistence API (JPA), Maps Java objects to database tables using
//Object-relational mapping (ORM), Spring data JPA allows for connection to database
import javax.validation.constraints.NotEmpty;

@Data //Creates the getters and setters
@AllArgsConstructor

public class Player2 extends Player{
    Player2(Player Pl){
        this.id_2 = Pl.getId();
        this.elo_2 = Pl.getElo();
        this.name_2 = Pl.getName();
    }
    private Long id_2;
    private Double elo_2;
    private String name_2;
}
