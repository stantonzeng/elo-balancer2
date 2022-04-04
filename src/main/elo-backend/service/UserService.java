package service;

import model.User;

import java.util.List;

public interface UserService {

    User create(User _user);

    User update(User _user);

    User get(String name);

    List<User> fullList();

}
