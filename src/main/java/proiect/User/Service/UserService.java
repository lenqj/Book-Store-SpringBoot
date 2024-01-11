package proiect.User.Service;

import org.springframework.data.repository.query.Param;
import proiect.DTO.UserDto;
import proiect.Model.User.RegistrationRequest;
import proiect.Model.User.User;
import proiect.Model.User.UserRole;

import java.util.List;

public interface UserService {

    boolean checkEmail(String email);

    UserDto registerUser(RegistrationRequest registrationRequest);

    UserDto getLoginUser();

    UserDto getUserById(Integer id);

    List<UserDto> getAllUsers();
    List<User> findAll();


    UserDto createUser(User user);

    UserDto updateUser(User user);

    void deleteUser(User user);
    void deleteById(Integer ID);

}