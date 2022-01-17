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

    Collection<Player> list(int limit);

    List<Player> listTest();

    Teams balanceTeams();

    List<Team> printOutTeams();
}
