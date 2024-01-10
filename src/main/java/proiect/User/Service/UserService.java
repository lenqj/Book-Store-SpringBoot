package proiect.User.Service;

import proiect.DTO.UserDto;
import proiect.Model.RegistrationRequest;
import proiect.Model.User.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean checkEmail(String email);

    UserDto registerUser(RegistrationRequest registrationRequest);

    UserDto getLoginUser();

    UserDto getUserById(Integer id);

    List<UserDto> getAllUsers();

    UserDto createUser(User user);

    UserDto updateUser(User user);

    void deleteUser(User user);
}