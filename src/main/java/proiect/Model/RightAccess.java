package proiect.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class RightAccess extends AbstractEntity {
    private String rightAccess;
    @ManyToMany
    private List<UserRole> userRoles;
    public RightAccess(String rightAccess){
        this.rightAccess = rightAccess;
    }

    public RightAccess() {
    }

    public String getRight() {
        return rightAccess;
    }

    public void setRight(String right) {
        this.rightAccess = right;
    }

    public List<UserRole> getRoles() {
        return userRoles;
    }

    public void setRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
