package io.balancer.elo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*; //Comes from Java Persistence API (JPA), Maps Java objects to database tables using
//Object-relational mapping (ORM), Spring data JPA allows for connection to database
import javax.validation.constraints.NotEmpty;

@Data //Creates the getters and setters
@AllArgsConstructor
public class Player1 extends Player{
    Player1(Player Pl){
        this.id_1 = Pl.getId();
        this.elo_1 = Pl.getElo();
        this.name_1 = Pl.getName();
    }
    private Long id_1;
    private Double elo_1;
    private String name_1;

}
