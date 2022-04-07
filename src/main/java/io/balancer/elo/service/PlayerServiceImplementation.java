package io.balancer.elo.service;

import io.balancer.elo.PlayerRepository;
import io.balancer.elo.UserRepository;
import io.balancer.elo.model.Player;
import io.balancer.elo.model.Team;
import io.balancer.elo.model.Teams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final UserRepository _userRepo;

    private Teams _teams;

    public List<Player> postedPlayers;

    public List<Player> setPostedPlayers(List<Player> _postedPlayers) {
        this.postedPlayers = _postedPlayers;
        // log.info("Setting the players in");
        // for (Player postedPlayer : _postedPlayers) {
        //     log.info("Adding Player: {}", postedPlayer.getName());
        // }
        return this._playerRepo.findAll();
    }

    public PlayerServiceImplementation(PlayerRepository playerRepo, UserRepository userRepo) {
        _playerRepo = playerRepo;
        _userRepo = userRepo;
    }

    @Override
    public Player create(Player player) {
        // log.info("Saving new player: {}", player.getName());
        return this._playerRepo.save(player);
    }

    @Override
    public Player update(Player player) {
        // log.info("Updating player {}'s information {}", player.getName(), player.getId());
        return _playerRepo.save(player);
    }

    @Override
    public Player get(Long id) {
        // log.info("Fetching player {}", id);
        return _playerRepo.findById(id).get();
    }

    @Override
    public List<Player> list(int start, int limit) {
        // log.info("Fetching players");
        return _playerRepo.findAll(PageRequest.of(start, limit)).toList();
    }

    @Override
    public List<Player> fullList(){
        // log.info("Fetching all players");
        return _playerRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<Player> readString(String listP){
        // log.info(listP);
        List<Player> answ = new ArrayList<>();
        int start = 0;
        int end = 0;

        for(int i = 1; i < listP.length(); i++){
            if(listP.charAt(i) == ','){
                start = end;
                end = i;
                answ.add(get(Long.valueOf(listP.substring(start+1, end))));
            }
        }
        answ.add(get(Long.valueOf(listP.substring(end+1))));
        return answ;
    }
    //TODO: I think we can do this better. Rather than have a string of the user, we can just send in the entire user
    //This might potentially save time since we do not have to make "find by username" type of deal.
    @Override
    public List<Player> fullListUser(String user){
        // log.info("Fetching all players from User List: {}", user);
        String t = _userRepo.findByuserName(user).get(0).getListOfPlayers();
        if(t.length() == 0){
            return new ArrayList<>();
        }
        return readString(t);
    }

    void returnCorrectedVector(Vector<Integer> v, int i, int sz){
//        log.info(String.format("Sending in i at: %2d", i));
        if(i == 0){
            int t = v.get(0);
            v.set(0, t+1);
            return;
        }
        int offset = abs(i - (v.size()-1));

        if(v.get(i) >= (sz-1)-offset){
            returnCorrectedVector(v, i-1, sz);

            int t = v.get(i-1);
            v.set(i, t+1);
        }
        else{
            int t = v.get(i);
            v.set(i, t+1);
        }
    }

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
        // log.info("Starting Balancing Teams");

        //p will hold all the players (Up to size 10) taken by listTest()
        int sizeLimit = postedPlayers.size()/2;

        List<Player> t1 = new ArrayList<>();
        List<Player> t2 = new ArrayList<>();

        Teams implementedTeams = new Teams();
        double sumT1 = 0;
        double sumT2 = 0;

        // log.info("Starting Teams base");

        for(Player players : postedPlayers){
            t1.add(players);
            sumT1 += players.getElo();
        }

        implementedTeams.addToList(new Team(t1, t2, sumT1, sumT2));

        for(int counter = 0; counter < sizeLimit; counter++){
//            log.info(String.format("Counter: %2d", counter));
            Vector<Integer> pointHolder = new Vector<>();

            for(int point = 0; point < counter+1; point++){
                pointHolder.add(point);
            }

            while(pointHolder.get(0) <= (t1.size()- pointHolder.size())){
//                log.info(String.format("t1.size() - pointholder.size() = %2d - %2d = %2d", t1.size(), pointHolder.size(), t1.size()- pointHolder.size()));
//                for(int i = 0; i < pointHolder.size(); i++){
//                    log.info(String.format("i: %2d, val: %2d", i, pointHolder.get(i)));
//                }
                List<Player> tempT1 = new ArrayList<>(t1);
                List<Player> tempT2 = new ArrayList<>(t2);


                double tempSumT1 = sumT1;
                double tempSumT2 = sumT2;

                for (Integer i : pointHolder) {

                    //Give team 2 the player index from team 1

                    Player e = tempT1.get(i);
                    tempT2.add(e);
                    tempSumT2 += e.getElo();
                    tempSumT1 -= e.getElo();
                }
                for(int i = pointHolder.size()-1; i >= 0; i--){
                    //Remove the players from team 1 (we do it backwards to not screw up index positioning)
                    int g = pointHolder.get(i);
                    tempT1.remove(g);
                }
                tempT1.sort(new Comparator<Player>() {
                    @Override
                    public int compare(Player o1, Player o2) {
                        if(Objects.equals(o1.getId(), o2.getId())) return 0;
                        return o1.getId() < o2.getId() ? -1 : 1;
                    }
                });
                tempT2.sort(new Comparator<Player>() {
                    @Override
                    public int compare(Player o1, Player o2) {
                        if(Objects.equals(o1.getId(), o2.getId())) return 0;
                        return o1.getId() < o2.getId() ? -1 : 1;
                    }
                });

                implementedTeams.addToList(new Team(tempT1, tempT2, tempSumT1, tempSumT2));
                returnCorrectedVector(pointHolder, pointHolder.size()-1, postedPlayers.size());
            }

        }

        // log.info("Returning Balanced Teams");
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
        // log.info("Starting to make Teams");
        return this.balanceTeams().getTeams(amount);
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
