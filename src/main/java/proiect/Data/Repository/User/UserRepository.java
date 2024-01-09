package proiect.Data.Repository.User;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import proiect.Model.User.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username = :username and u.password = :password")
    User findUserByUsernameAndPassword(@Param("username") String username,
                                         @Param("password") String password);

}
