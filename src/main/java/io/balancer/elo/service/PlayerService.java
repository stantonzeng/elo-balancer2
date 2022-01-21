package io.balancer.elo.service;

import io.balancer.elo.model.Player;
import io.balancer.elo.model.Team;
import io.balancer.elo.model.Teams;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public interface PlayerService {
    Player create(Player _player);

    Player update(Player _player);

    Player get(Long id);

    List<Player> list(int start, int limit);

    List<Player> fullList();

    Teams balanceTeams(int num);

    /*
    Overall, we have 3 different classes, a Player class, a Team class, and a Teams class (creative, I know...). The Team class
    will be consisting of Players, and the Teams class will be consisting of Team.

    Players will hold an elo, username, id, etc.

    The Team class holds these list of players in order of team 1 and team 2. Each time we will produce the sum elo of Team 1 and
    Team 2, as well as record the difference between the two elo's.

    The Teams class will be holding a simple priority queue of the Team class that will be sorted based on elo differential.

    Therefore, if we want to pull, lets say the 10 best teams possible, then we
    just call the Teams Priority queue and pop out the first 10 values.
     */

    List<Team> printOutTeams(int amount);
}
