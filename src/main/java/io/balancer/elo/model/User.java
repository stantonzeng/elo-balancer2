package io.balancer.elo.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity //Turns our class into an entity so we can map it to db
@Data
@NoArgsConstructor
@Table(name = "Users")
@Slf4j
public class User {
    public User(){
        
    }

    public User(Long idVal, String userNameVal, String listVal){
        this.id = idVal;
        this.userName = userNameVal;
        this.listOfPlayers = listVal;
    }

    @Id //Identifies the primary key for the @Entity value
    @GeneratedValue(strategy = GenerationType.AUTO) //HOW we generate our Id's for players
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Name cannot be empty")
    @Getter(AccessLevel.NONE)
    private String userName;


    private String listOfPlayers;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @return the listOfPlayers
     */
    public String getListOfPlayers() {
        return listOfPlayers;
    }
    /**
     * @param listOfPlayers the listOfPlayers to set
     */
    public void setListOfPlayers(String listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public String addToList(String val){
        this.listOfPlayers = this.listOfPlayers + "," + val;
        return val;
    }

}
