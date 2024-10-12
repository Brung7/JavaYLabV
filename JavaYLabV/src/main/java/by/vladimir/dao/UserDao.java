package by.vladimir.dao;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserDao {
    private static final UserDao INSTANCE = new UserDao();
    private UserDao() {
    }
    public static UserDao getInstance() {
        return INSTANCE;
    }
    private static final Map<String, User> users = new HashMap<>();
    public boolean save(String email, String password, Role role) {
        if (!users.containsKey(email)) {
            users.put(email, new User(email, password, role));
            return true;
        } else {
            return false;
        }
    }
    public User findByEmail(String email) {
        if (users.containsKey(email)) {
            return users.get(email);
        }
        return null;
    }
    public void update(User user){
        if(users.containsKey(user.getEmail())){
            users.put(user.getEmail(),user);
        }
    }
    public void delete(String email){
        users.remove(email);
    }
    public List<User> getListOfUsers(){
        List<User> userList = new ArrayList<>(users.values());
        return userList;
    }
    public Map<String,User> getUsers(){
        return users;
    }


}
