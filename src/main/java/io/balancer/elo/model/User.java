package io.balancer.elo.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity //Turns our class into an entity so we can map it to db
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
@Slf4j
public class User {
    @Id //Identifies the primary key for the @Entity value
    @GeneratedValue(strategy = GenerationType.AUTO) //HOW we generate our Id's for players
    private Long id;

    @Column(unique = true) //All name values have to be unique, maps the value name to a database column
    @NotEmpty(message = "Name cannot be empty")
    @Getter(AccessLevel.NONE)
    private String userName;


    private String listOfPlayers;

    public String addToList(String val){
        this.listOfPlayers = this.listOfPlayers + "," + val;
        log.info(this.listOfPlayers);
        log.info("Adding player {} to user {}", val, userName);
        return val;
    }

    public String getUserName() {
        return userName;
    }

}