package by.vladimir.service;

import by.vladimir.dao.HabitDao;
import by.vladimir.dto.CreateUserDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.dao.UserDao;
import by.vladimir.mapper.UserMapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserService {
    @Getter
    private static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    UserDao userDao = UserDao.getInstance();
    UserMapper userMapper = UserMapper.getInstance();

    public User registration(CreateUserDto createUserDto) {
        User user = userMapper.mapFrom(createUserDto);
        return userDao.save(user);
    }

    public Optional<User> authentication(String email) {
        Optional<User> userOptional = userDao.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional;
        } else {
            return Optional.empty();
        }
    }

    public boolean isUserExist(String email) {
        if (userDao.findByEmail(email).isPresent()) {
            return true;
        } else {
            return false;
        }
    }


    public List<User> userList() {
        return userDao.getListOfUsers();
    }

    public List<UserDto> getAllUsersWithHabits() {
        List<UserDto> userDtos = userDao.getAllHabitsOfAllUsers();
        return userDtos;
    }
}
