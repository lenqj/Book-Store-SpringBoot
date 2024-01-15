package proiect.User.Service;

import proiect.DTO.UserDto;
import proiect.Model.User.RegistrationRequest;
import proiect.Model.User.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean checkEmail(String email);

    UserDto registerUser(RegistrationRequest registrationRequest);

    UserDto getLoginUser();

    UserDto getUserById(Integer id);

    List<UserDto> getAllUsers();
    List<User> findAll();


    UserDto createUser(User user);

    User updateUser(UserDto user, String password);

    void deleteUser(User user);
    void deleteById(Integer ID);
    Optional<User> findById(Integer ID);
    void save(User user);
}