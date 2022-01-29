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

    private Teams _teams;

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
    public List<Player> list(int start, int limit) {
        log.info("Fetching players");
        return _playerRepo.findAll(PageRequest.of(start, limit)).toList();
    }

    @Override
    public List<Player> fullList(){
        log.info("Fetching all players");
        return _playerRepo.findAll();
    }


    //This is used for sorting, if I want to sort the list of players again, I can implement this back
    //Reason for commenting it out: Sorting doesn't work with limits on players for some reason...
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
        List<Player> p = list(0, 10);
//        log.info("Sorting");
//        p.sort(compareByElo);
//        log.info("Sorting finished");

        List<Player> t1 = new ArrayList<>();
        List<Player> t2 = new ArrayList<>();

        Teams implementedTeams = new Teams();

        double sumT1 = 0;
        double sumT2 = 0;

        //We can heuristically generate the teams by just doing even-odd separation
        //Fix this for loop, it is very messy and will produce a lot of errors

        log.info("Starting Teams base");

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

        log.info("Finished Creating original Team");

        //At this point, we have our two even-odd lists that are ordered, we can now get the
        //values we need

        implementedTeams.addToList(new Team(t1, t2, sumT1, sumT2));

        //We will use these 2 values to help with swapping players
        Player tempSwap = null;
        Player tempSwap2 = null;

        int pos2 = 0;

        // |------------------------------------------- SINGLE SWAP START -------------------------------------------|
        for(int i = 0; i < t1.size(); i++){

            /*
            For the single swap, we want to find the two values that will minimize the elo differential between the two
            teams. t1 and t2 ARE NOT OPTIMAL, thus we must check and find the most optimal one.
            */
            pos2 = findPosition(t2, sumT1, sumT2, t1.get(i).getElo(), -1);
            tempSwap = t2.get(pos2);

            //Once we get the lowest elo differential possible we do a swap of the best possible result
            //tempSumT1 and tempSumT2 holds the elo's for the newly swapped teams.
            double tempSumT1 = sumT1 - t1.get(i).getElo() + tempSwap.getElo();
            double tempSumT2 = sumT2 + t1.get(i).getElo() - tempSwap.getElo();
            List<Player> swappedT1 = new ArrayList<>(t1);
            List<Player> swappedT2 = new ArrayList<>(t2);

            swappedT2.set(pos2, swappedT1.get(i));
            swappedT1.set(i, tempSwap);

//            log.info("Switching values at index " + i + " with " + pos2);
//            log.info("New Set: " + tempT.printAllPlayers());

            //The swap should work... test to make sure

            //Assign these two values to a team and min heap the team using elo diff
            //Pushing the tempTeam with its elo difference of the teams into the priority queue of Teams
            implementedTeams.addToList(new Team(swappedT1, swappedT2, tempSumT1, tempSumT2));
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
        */
        for(int i = 0; i < t1.size(); i++){
            for(int l = 0; l < t2.size(); l++){
                tempSwap = t2.get(l);
                for(int k = 0; k < t1.size(); k++){
                    if(k == i)
                        continue;

                    /*
                    Here, similar to the single swap method, we can input the sum elo of both team 1 and team 2, but this time since
                    we know that we are testing it if we use pointer i and l, then we can use the same function, "findPosition", but send
                    in the elo's

                     */
                    int newPosition2 = findPosition(t2, sumT1-t1.get(i).getElo(), sumT2-t2.get(l).getElo(), t1.get(k).getElo(), l);
                    tempSwap2 = t2.get(newPosition2);

                    double tempSumT1 = sumT1 - t1.get(i).getElo() - t1.get(k).getElo() + tempSwap.getElo() + tempSwap2.getElo();
                    double tempSumT2 = sumT2 + t1.get(i).getElo() + t1.get(k).getElo() - tempSwap.getElo() - tempSwap2.getElo();
                    List<Player> swappedT1 = new ArrayList<>(t1);
                    List<Player> swappedT2 = new ArrayList<>(t2);

                    swappedT2.set(l, swappedT1.get(i));
                    swappedT2.set(newPosition2, swappedT1.get(k));
                    swappedT1.set(i, tempSwap);
                    swappedT1.set(k, tempSwap2);

                    //The swap should work... test to make sure

                    //Assign these two values to a team and min heap the team using elo diff
                    //Pushing the tempTeam with its elo difference of the teams into the priority queue of Teams
                    implementedTeams.addToList(new Team(swappedT1, swappedT2, tempSumT1, tempSumT2));
                }
            }
        }



        // |------------------------------------------- DOUBLE SWAP END-------------------------------------------|
        log.info("Returning Balanced Teams");
        this._teams = implementedTeams;
        return implementedTeams;
    }

    /*
    Logic behind the findPosition function:

    So when we want to minimize the elo differential, we can just instead take the original sum of the both the team's elo's
    and just compare what it would be like if we switched two of the indexes. Thus, the strange math for val is just a
    simple representation of "switching". We take and record whichever index of t2 gives us the lowest value and return
    the index.
    */

    private int findPosition(List<Player> t2, double eloSumT1, double eloSumT2, double index1Elo, int check){
        int res = -1;
        double leastVal = Integer.MAX_VALUE;
        for(int j = 0; j < t2.size(); j++){
            if(j == check)
                continue;

            double val = abs((eloSumT1 - index1Elo + t2.get(j).getElo()) - (eloSumT2 + index1Elo - t2.get(j).getElo()));

            if(val < leastVal){
                res = j;
                leastVal = val;
            }
        }
        return res;
    }

    /*
    Some Notes on this function

    This function is basically useless, but it helps make everything look pretty?

    balanceTeams(int num) returns a Teams object that holds a priority queue of the sorted teams.
    The "num" part of it is also essentially useless, but it switches which side the teams are on. If num == 0,
    then team 1 will be team 1, but if num == 1, then the team 1 of num == 0 will switch to team 2. This doesn't do
    much, but it might provide functionality in the future, so im keeping it.

    getTeams(int num) returns the sorted teams in a list format, but at a limit of num. If you want the 10 best teams,
    then num == 10.
     */

    @Override
    public List<Team> printOutTeams(int amount){
        log.info("Starting to make Teams");

        return this.balanceTeams(0).getTeams(amount);
    }

    @Override
    public String gameResults(int index, int win) throws Exception {
        Team t = this._teams.adjustElo(index, win);
        for(int i = 0; i < t.getTeam1().size(); i++){
            update(t.getTeam1().get(i));
        }
        for(int i = 0; i < t.getTeam2().size(); i++){
            update(t.getTeam2().get(i));
        }
        return "Team " + win+1 + " won";
    }
}
