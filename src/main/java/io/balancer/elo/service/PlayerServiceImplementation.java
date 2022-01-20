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
    public Teams balanceTeams(int num){
        log.info("Starting Balancing Teams");

        //p will hold all the players (Up to size 10) taken by listTest()
        List<Player> p = listTest();
        p.sort(compareByElo);

        List<Player> t1 = new ArrayList<>();
        List<Player> t2 = new ArrayList<>();

        Teams implementedTeams = new Teams();

        double sumT1 = 0;
        double sumT2 = 0;

        //We can heuristically generate the teams by just doing even-odd separation
        //Fix this for loop, it is very messy and will produce a lot of errors

        for(int i = 0; i < p.size(); i++){
            if(i%2 == num){
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

        Team logger = new Team(t1, t2, sumT1, sumT2);
        log.info("Original Set: " + logger.printAllPlayers());

//        implementedTeams.addToList(new Team(t1, t2, sumT1, sumT2));

        //We will use these 2 values to help with swapping players
        Player tempSwap = null;
        int pos2 = 0;

        Player tempSwap2 = null;
        int otherPos2 = 0;

        // |------------------------------------------- SINGLE SWAP START -------------------------------------------|
        for(int i = 0; i < t1.size(); i++){

            double tempSumT1 = sumT1;
            double tempSumT2 = sumT2;

            List<Player> swappedT1 = new ArrayList<>(t1);
            List<Player> swappedT2 = new ArrayList<>(t2);

            /*
            For the single swap, we want to find the two CLOSEST elo values from t1 and t2
            The intuition behind this is that when only swapping one player from each team,
            we naturally want to minimize the elo difference in the teams. Thus,
            */
            pos2 = findPosition(t2, t1.get(i).getElo(), Integer.MAX_VALUE, -1);
            tempSwap = t2.get(pos2);

            //Once we get the lowest elo differential possible we do a swap of the best possible result
            //tempSumT1 and tempSumT2 holds the elo's for the newly swapped teams.
            tempSumT1 = tempSumT1 - swappedT1.get(i).getElo() + tempSwap.getElo();
            tempSumT2 = tempSumT2 + swappedT1.get(i).getElo() - tempSwap.getElo();
            swappedT2.set(pos2, swappedT1.get(i));
            swappedT1.set(i, tempSwap);

            Team tempT = new Team(swappedT1, swappedT2, tempSumT1, tempSumT2);


            log.info("Switching values at index " + i + " with " + pos2);
            log.info("New Set: " + tempT.printAllPlayers());

            //The swap should work... test to make sure

            //Assign these two values to a team and min heap the team using elo diff
            //Pushing the tempTeam with its elo difference of the teams into the priority queue of Teams
            implementedTeams.addToList(tempT);
        }

        // |------------------------------------------- SINGLE SWAP END -------------------------------------------|

        // |------------------------------------------- DOUBLE SWAP START-------------------------------------------|

        /*
        i == first pointer for t1
        l == first pointer for t2
        k == second pointer for t1
        otherPos2 = second pointer for t2

        i != k
        and l != otherPos2

        Small tid bit explanation for the code below

        The original plan was to just find the t1[i] and t2[pos2] that would minimize the elo differences. Well this mostly works for single swap
        but now that we can swap two players, we can actually check every single t1[i] and t2[l] and see if t1[k] (which != t1[i]) and t2[otherPos2]
        can minimize the elo differences. This will help create more legit and dynamic teams.

        This is def O(n^4), but since our size is so small (like 12 players being checked at most), then we really don't care about run time.

        The original iteration is above commented out above for O(n^3).
        */


        /*
        for(int i = 0; i < t1.size(); i++){

            for(int l = 0; l < t2.size(); l++){

                //Tracker now represents the elo difference from the randomly checked values of t1[i] and t2[l]
                double tracker = abs(t1.get(i).getElo()-t2.get(l).getElo());
                tempSwap = t2.get(l);
                for(int k = 0; k < t1.size(); k++){

                    if(k == i)
                        continue;

                    double tempSumT1 = sumT1;
                    double tempSumT2 = sumT2;

                    List<Player> swappedT1 = new ArrayList<>(t1);
                    List<Player> swappedT2 = new ArrayList<>(t2);

                    //Here, we use findPosition to minimize whatever value we got from tracker
                    otherPos2 = findPosition(t2, t1.get(k).getElo(), tracker, l);
                    tracker = abs(t1.get(k).getElo()-t2.get(otherPos2).getElo()); //Fix this, the tracker method does not work for the double swap
                    //We cannot just look for closest 0 value, we have to take into account
                    tempSwap2 = t2.get(otherPos2);

                    tempSumT1 = tempSumT1 - swappedT1.get(i).getElo() - swappedT1.get(k).getElo() + tempSwap.getElo() + tempSwap2.getElo();
                    tempSumT2 = tempSumT2 + swappedT1.get(i).getElo() + swappedT1.get(k).getElo() - tempSwap.getElo() - tempSwap2.getElo();
                    swappedT2.set(l, swappedT1.get(i));
                    swappedT2.set(otherPos2, swappedT1.get(k));
                    swappedT1.set(i, tempSwap);
                    swappedT1.set(k, tempSwap2);

                    //The swap should work... test to make sure

                    //Assign these two values to a team and min heap the team using elo diff
                    Team tempTeam = new Team(swappedT1, swappedT2, tempSumT1, tempSumT2);
                    tempTeam.setEloDifference(tracker);

                    //Pushing the tempTeam with its elo difference of the teams into the priority queue of Teams
                    implementedTeams.addToList(tempTeam);
                }
            }
        }

        */

        // |------------------------------------------- DOUBLE SWAP END-------------------------------------------|
        log.info("Returning Balanced Teams");

        return implementedTeams;
    }

    //FIX THIS!!! We still are not getting the smallest elo differential. Do the math tomorrow
    public int findPosition(List<Player> t2, double elo, double tracker, int check){
        int res = -1;
        for(int j = 0; j < t2.size(); j++){
            if(j == check)
                continue;

            double otherE2 = t2.get(j).getElo();

            //if the difference of the two new points are less than the old tracker,
            //then we've found a more balanced set of two points
            if(abs(elo-otherE2) < tracker){
                res = j;
                tracker = abs(elo-otherE2);
            }
        }

        return res;
    }

    @Override
    public List<Team> printOutTeams(){
        log.info("Starting to make Teams");
        log.info("Printing out Teams");
        return this.balanceTeams(0).getTeams();
    }
}
