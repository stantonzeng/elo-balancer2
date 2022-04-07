package io.balancer.elo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GameResult {

//    private int index;
    private int teamNumb;

    /**
     * @return the teamNumb
     */
    public int getTeamNumb() {
        return teamNumb;
    }

    /**
     * @param teamNumb the teamNumb to set
     */
    public void setTeamNumb(int teamNumb) {
        this.teamNumb = teamNumb;
    }

}
