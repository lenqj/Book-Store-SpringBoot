package proiect.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import proiect.Model.User.UserDetails;

import java.util.List;
@Entity
public class UserRole extends AbstractEntity{
    private String role;
    @ManyToMany(mappedBy = "userRoles")
    private List<RightAccess> rightAccesses;
    @OneToMany(mappedBy = "userRole")
    private List<UserDetails> usersDetails;
    public UserRole(String role, List<RightAccess> rightAccesses) {
        this.role = role;
        this.rightAccesses = rightAccesses;
    }

    public UserRole() {

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<RightAccess> getRights() {
        return rightAccesses;
    }

    public void setRights(List<RightAccess> rightAccesses) {
        this.rightAccesses = rightAccesses;
    }

    @Override
    public String toString() {
        return role;
    }
}
