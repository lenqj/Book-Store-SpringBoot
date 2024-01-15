package proiect.User.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import proiect.DTO.UserDto;
import proiect.Mapper.RoleMapper;
import proiect.Mapper.UserMapper;
import proiect.Model.User.RegistrationRequest;
import proiect.User.Repository.UserRoleRepository;
import proiect.User.Repository.UserRepository;
import proiect.Model.User.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmailAddress(email);
    }
    @Override
    public UserDto registerUser(RegistrationRequest registrationRequest) {
        User user = User.builder()
                .username(registrationRequest.getUsername())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .password(registrationRequest.getPassword())
                .emailAddress(registrationRequest.getEmailAddress())
                .role((roleRepository.findByName("USER")))
                .build();


        return this.createUser(user);
    }

    public UserDto getLoginUser(){
        return userMapper.userEntityToDto(Objects.requireNonNull(userRepository.findLoginUser().orElse(null)));
    }

    public UserDto getUserById(Integer id){
        return userMapper.userEntityToDto(userRepository.findById(id).orElse(null));
    }
    public List<UserDto> getAllUsers(){
        return userMapper.userListEntityToDto(userRepository.findAll());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public UserDto createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.userEntityToDto(userRepository.save(user));
    }

    public User updateUser(UserDto user, String password){
        return userRepository.save(userMapper.userDtoToEntity(user, password));
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Integer ID) {
        userRepository.deleteById(ID);
    }

    @Override
    public Optional<User> findById(Integer ID) {
        return userRepository.findById(ID);
    }
    public void save(User user) {
        userRepository.save(user);
    }
}
