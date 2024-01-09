package proiect.Data.Service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiect.Data.Repository.User.RoleRepository;
import proiect.Data.Repository.User.UserDetailsRepository;
import proiect.Data.Repository.User.UserRepository;
import proiect.Model.User.User;
import proiect.Model.User.UserDetails;
import proiect.Model.UserRole;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public void save(User user) {
        UserRole role = roleRepository.findByName("ROLE_USER");
        if(role == null){
            role = deafultUserRole();
        }
        UserDetails userDetails = deafultUserDetails(role);
        user.setUserDetails(userDetails);
        //user.setPassword(Utils.hashPassword(user.getPassword()));
        userRepository.save(user);
    }
    public User findUserByUsernameAndPassword(String username, String password){
        return userRepository.findUserByUsernameAndPassword(username, password);
    }
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
    public UserRole deafultUserRole(){
        UserRole role = new UserRole();
        role.setRole("ROLE_USER");
        return roleRepository.save(role);
    }
    public UserDetails deafultUserDetails(UserRole role){
        UserDetails userDetails = new UserDetails();
        userDetails.setMoney(0L);
        userDetails.setUserRole(role);
        return userDetailsRepository.save(userDetails);
    }
}
