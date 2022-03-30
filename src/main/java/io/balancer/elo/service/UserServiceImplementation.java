package io.balancer.elo.service;

import io.balancer.elo.UserRepository;
import io.balancer.elo.model.Player;
import io.balancer.elo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImplementation implements UserService{

    private final UserRepository _userRepo;
    private final PlayerServiceImplementation _playerServiceImplementation;

    public UserServiceImplementation(UserRepository userRepo, PlayerServiceImplementation playerServiceImplementation) {
        _userRepo = userRepo;
        _playerServiceImplementation = playerServiceImplementation;
    }

    @Override
    public User create(User _user) {
        log.info("Creating new User: {}", _user.getUserName());
        return this._userRepo.save(_user);
    }

    @Override
    public User update(User _user) {
        log.info("Updating new User: {}", _user.getUserName());
        return this._userRepo.save(_user);
    }

    @Override
    public User get(String name) {
        log.info("Finding User: {}", name);
        List<User> user = this._userRepo.findByuserName(name);

        if(user.size() == 0){
            log.info("{} not found", name);
            return null;
        }

        return user.get(0);

    }

    @Override
    public List<User> fullList(){
        log.info("Fetching all users");
        return _userRepo.findAll(Sort.by(Sort.Direction.ASC, "userName"));
    }

    @Override
    public List<Long> readString(String listP){
        List<Long> answ = new ArrayList<>();
        int start = 0;
        int end = 0;

        //012345678
        //,9,12,123
        for(int i = 1; i < listP.length(); i++){
            if(listP.charAt(i) == ','){
                start = end;
                end = i;
                answ.add(Long.valueOf(listP.substring(start+1, end)));
            }
        }
        answ.add(Long.valueOf(listP.substring(end+1)));
        return answ;
    }

    @Override
    public List<Player> fullListUser(String user){
        log.info("Fetching all players from User List: {}", user);

        List<Player> p = new ArrayList<>();
        String t = _userRepo.findByuserName(user).get(0).getListOfPlayers();
        log.info(_userRepo.findByuserName(user).get(0).getUserName());
        log.info("size of t {}", t.length());
        List<Long> test = readString(t);
        for(Long i: test){
            log.info(String.valueOf(i));
        }
        return p;
    }


}
