package io.balancer.elo.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.*;


@Data
@Slf4j
public class Teams {
    public Teams() {
        //At some point we will have to change initial Capacity
        this.teams = new PriorityQueue<Team>(10, new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                if(o1.getEloDifference() > o2.getEloDifference()) return 1;
                else if (o1.getEloDifference() < o2.getEloDifference()) return -1;
                else return 0;
            }
        });
        this.st = new HashSet<List<Player>>();
    }

    public void addToList(Team t){
//        log.info("Printing added players " + t.printAllPlayers());
        if(st.isEmpty() || !st.contains(t.getTeam1()) || !st.contains(t.getTeam2())) {
            this.teams.add((t));
            st.add(t.getTeam1());
            st.add(t.getTeam2());
        }
    }

    private PriorityQueue<Team> teams;

    private Set<List<Player>> st;

    private List<Team> sortedTeams = new ArrayList<>();

    public List<Team> getTeams(int cnt){
        if (cnt == -1)
            cnt = this.teams.size();
        while(!this.teams.isEmpty() && cnt > 0){
//            log.info(temp.poll().printAllPlayers() + "\n");
            sortedTeams.add(this.teams.poll());
            cnt--;
        }
        return this.sortedTeams;
    }

    public Team adjustElo(int index, int win) throws Exception {
        if(this.sortedTeams.size() == 0)
            throw new Exception("Exception message");

        return this.sortedTeams.get(index).adjustRatings(win);
    }
}
