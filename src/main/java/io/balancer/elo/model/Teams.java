package io.balancer.elo.model;

import lombok.Data;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

@Data
public class Teams {
    public Teams() {
        this.teams = new PriorityQueue<Team>(10, new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                if(o1.getEloDifference() > o2.getEloDifference()) return 1;
                else if (o1.getEloDifference() < o2.getEloDifference()) return -1;
                else return 0;
            }
        });
    }

    public void addEntireListToQueue(Teams t){
        Iterator it = t.getTeams().iterator();

        while(it.hasNext()){
            addToList((Team) it.next());
        }
    }

    public void addToList(Team t){
        this.teams.add((t));
    }

    private PriorityQueue<Team> teams;

    public PriorityQueue<Team> getTeams(){
        return this.teams;
    }
}
