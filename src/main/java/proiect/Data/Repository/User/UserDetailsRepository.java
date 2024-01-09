package proiect.Data.Repository.User;

import org.springframework.data.repository.CrudRepository;
import proiect.Model.User.User;
import proiect.Model.User.UserDetails;

public interface UserDetailsRepository extends CrudRepository<UserDetails, Integer> {
}
