package proiect.Data.Repository.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import proiect.Model.UserRole;

public interface RoleRepository extends CrudRepository<UserRole, Integer> {
    @Query("SELECT role FROM UserRole role WHERE role.role = :role")
    UserRole findByName(@Param("role") String role);
}
