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
import java.util.Set;

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
    public List<Player> fullListUser(String user){
        log.info("Fetching all players from User List: {}", user);

        List<Player> p = new ArrayList<>();
        List<Long> t = _userRepo.findByuserName(user).get(0).getListOfPlayers();
        log.info(_userRepo.findByuserName(user).get(0).getUserName());
        log.info("size of t {}", t.size());
        for(Long i : t){
            p.add(_playerServiceImplementation.get(i));
            log.info(String.valueOf(i));
        }
        return p;
    }


}
