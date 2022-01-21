package io.balancer.elo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*; //Comes from Java Persistence API (JPA), Maps Java objects to database tables using
//Object-relational mapping (ORM), Spring data JPA allows for connection to database
import javax.validation.constraints.NotEmpty;

@Entity //Turns our class into an entity so we can map it to db
@Data //Creates the getters and setters
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id //Identifies the primary key for the @Entity value
    @GeneratedValue(strategy = GenerationType.AUTO) //HOW we generate our Id's for players
    private Long id;
    private Double elo;

    @NotEmpty(message = "Name cannot be empty")
    @Column(unique = true) //All name values have to be unique, maps the value name to a database column
    private String name;

    private int streak;
    private int numGamesPlayed;

}
