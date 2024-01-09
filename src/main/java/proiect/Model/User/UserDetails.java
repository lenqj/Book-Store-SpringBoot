package proiect.Model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.PositiveOrZero;
import proiect.Model.AbstractEntity;
import proiect.Model.UserRole;


@Entity
public class UserDetails extends AbstractEntity {
    @ManyToOne
    private UserRole userRole;
    @PositiveOrZero(message = "Money should be greater than or equal to 0.")
    private Long money;

    public UserDetails(UserRole userRole, Long money) {
        this.userRole = userRole;
        this.money = money;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public UserDetails() {
    }
}
