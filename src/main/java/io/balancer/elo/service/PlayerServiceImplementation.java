package io.balancer.elo.service;

import io.balancer.elo.PlayerRepository;
import io.balancer.elo.model.Player;
import io.balancer.elo.model.Team;
import io.balancer.elo.model.Teams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static java.lang.Math.*;

//@RequiredArgsConstructor //Develops the constructors on its own... not really required
@Service
@Transactional
@Slf4j
public class PlayerServiceImplementation implements PlayerService{
    private final PlayerRepository _playerRepo; //final means can only be assigned once

    public PlayerServiceImplementation(PlayerRepository playerRepo) {
        _playerRepo = playerRepo;
    }

    @Override
    public Player create(Player player) {
        log.info("Saving new player: {}", player.getName());
        return this._playerRepo.save(player);
    }

    @Override
    public Player update(Player player) {
        log.info("Updating player {}'s information", player.getName());
        return _playerRepo.save(player);
    }

    @Override
    public Player get(Long id) {
        log.info("Fetching player {}", id);
        return _playerRepo.findById(id).get();
    }

    @Override
    public Collection<Player> list(int limit) {
        log.info("Fetching all players");
        return _playerRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public List<Player> listTest(){
        log.info("Fetching all players");
        return _playerRepo.findAll();
    }

    Comparator<Player> compareByElo = Comparator.comparing(Player::getElo);

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
    @Override
    public Teams balanceTeams(){
        log.info("Starting Balancing Teams");

        //p will hold all of the players (Up to size 10) taken by listTest()
        List<Player> p = listTest();
        p.sort(compareByElo);

        List<Player> t1 = new ArrayList<Player>();
        List<Player> t2 = new ArrayList<Player>();
        List<Player> swappedT1;
        List<Player> swappedT2;

        Teams implementedTeams = new Teams();


        double sumT1 = 0;
        double sumT2 = 0;

        //We can heuristically generate the teams by just doing even-odd separation
        //Fix this for loop, it is very messy and will produce a lot of errors

        for(int i = 0; i < p.size(); i++){
            if(i%2 == 0){
                t1.add(p.get(i));
                sumT1 += p.get(i).getElo();
            }
            else {
                t2.add(p.get(i));
                sumT2 += p.get(i).getElo();
            }
        }

        //At this point, we have our two even-odd lists that are ordered, we can now get the
        //values we need

        double differenceInTeams = abs(sumT1 - sumT2);

        //We will use these 2 values to help with swapping players
        Player tempSwap = null;
        int pos2 = 0;

        //Now we will generate all the possible values through single swap
        for(int i = 0; i < t1.size(); i++){

            double tempSumT1 = sumT1;
            double tempSumT2 = sumT2;
            double tracker = differenceInTeams;

            double e1 = t1.get(i).getElo();

            swappedT1 = t1;
            swappedT2 = t2;

            //Get the least value possible
            for(int j = 0; j < t2.size(); j++){
                double e2 = t2.get(j).getElo();

                if(abs(e1-e2) < tracker){
                    tempSwap = t2.get(j);
                    pos2 = j;
                    tracker = abs(e1-e2);
                }
            }

            //Once we get the lowest elo differential possible we do a swap of the best possible result
            //tempSumT1 and tempSumT2 holds the elo's for the newly swapped teams.
            tempSumT1 = tempSumT1 - swappedT1.get(i).getElo() + tempSwap.getElo();
            tempSumT2 = tempSumT2 + swappedT1.get(i).getElo() - tempSwap.getElo();
            swappedT2.set(pos2, swappedT1.get(i));
            swappedT1.set(i, tempSwap);

            //The swap should work... test to make sure

            //Assign these two values to a team and min heap the team using elo diff
            Team tempTeam = new Team(swappedT1, swappedT2, tempSumT1, tempSumT2);
            tempTeam.setEloDifference(tracker);

            //Pushing the tempTeam with its elo difference of the teams into the priority queue of Teams
            implementedTeams.addToList(tempTeam);
        }

        log.info("Returning Balanced Teams");

        return implementedTeams;
    }

    @Override
    public List<Team> printOutTeams(){
        log.info("Starting to make Teams");
        List<Team> res = new ArrayList<Team>();
        Teams listTeams = this.balanceTeams();

        Iterator it = listTeams.getTeams().iterator();

        while(it.hasNext()){
            res.add((Team) it.next());
        }

        log.info("Printing out Teams");
        return res;
    }
}
