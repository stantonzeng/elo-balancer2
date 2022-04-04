package service;

import model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImplementation implements UserService{

    private final UserRepository _userRepo;

    public UserServiceImplementation(UserRepository userRepo) {
        _userRepo = userRepo;
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


}
