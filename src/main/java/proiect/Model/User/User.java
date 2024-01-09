package proiect.Model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;
import proiect.Model.AbstractEntity;

@Entity
public class User extends AbstractEntity {

    @Column(unique=true)
    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    private String username;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$", message = "password must contain 1 number (0-9)\n" +
            "password must contain 1 uppercase letters\n" +
            "password must contain 1 lowercase letters\n" +
            "password must contain 1 non-alpha numeric number\n" +
            "password is 8-16 characters with no space")
    @Size(min = 6, message = "Username must be between 3 and 50 characters.")
    private String password;
    @OneToOne
    private UserDetails userDetails;

    public User(String username, String password, UserDetails userDetails) {
        this.username = username;
        this.password = password;
        this.userDetails = userDetails;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
