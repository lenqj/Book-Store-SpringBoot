package proiect.Data.Service.User;

import proiect.Model.User.User;

public interface UserService {
    void save(User user);
    User findUserByUsernameAndPassword(String username, String password);
    Iterable<User> findAll();
}