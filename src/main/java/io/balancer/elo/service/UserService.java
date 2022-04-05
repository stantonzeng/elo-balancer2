package io.balancer.elo.service;

import io.balancer.elo.model.User;

import java.util.List;

public interface UserService {

    User create(User _user);

    User update(User _user);

    User get(String name);

    List<User> fullList();

}
