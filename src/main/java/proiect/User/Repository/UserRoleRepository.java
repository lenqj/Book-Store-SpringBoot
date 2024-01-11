package proiect.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proiect.Model.User.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    @Query("SELECT role FROM UserRole role WHERE role.role = :role")
    UserRole findByName(@Param("role") String role);
}
