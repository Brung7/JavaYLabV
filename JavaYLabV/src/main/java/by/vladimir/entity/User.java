package by.vladimir.entity;
import java.util.List;
import java.util.Objects;
public class User {
    private String email;
    private String password;
    private Role role;

    private List<Habit> listOfHabits;

    public User(String email, String password, Role role, List<Habit> listOfHabits) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.listOfHabits = listOfHabits;
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Habit> getListOfHabits() {
        return listOfHabits;
    }

    public void setListOfHabits(List<Habit> listOfHabits) {
        this.listOfHabits = listOfHabits;
    }
}