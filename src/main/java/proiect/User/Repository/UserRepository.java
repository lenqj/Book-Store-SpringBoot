package proiect.User.Repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proiect.Model.User.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username = :username and u.password = :password")
    Optional<User> findUserByUsernameAndPassword(@Param("username") String username,
                                         @Param("password") String password);
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH)
    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.username = ?#{ principal.username}")
    Optional<User> findLoginUser();
    boolean existsByEmailAddress(String emailAddress);
}
