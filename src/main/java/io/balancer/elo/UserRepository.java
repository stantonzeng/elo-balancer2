package io.balancer.elo;

import io.balancer.elo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByuserName(String userName);
}
