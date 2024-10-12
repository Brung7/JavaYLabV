package by.vladimir.service;

import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.dao.UserDao;

import java.util.List;
import java.util.Optional;


public class UserService {
    private static final UserService INSTANCE = new UserService();
    private UserService(){}
    public static UserService getINSTANCE() {
        return INSTANCE;
    }
    UserDao userDao = UserDao.getInstance();
    public boolean registration(String email, String password, Role role){
        return userDao.save(email, password, role);
    }
    public Optional<User> authentication(String email) {
        User user = userDao.findByEmail(email);
        Optional<User> userOptional = Optional.ofNullable(user);
        if (userOptional.isPresent()) {
                return Optional.of(user);
            }
        else {
            return Optional.empty();
        }
    }
    public String getRole(User user){
        return user.getRole().toString();
    }

    public List<User> userList(){
        return userDao.getListOfUsers();
    }
}
