package by.vladimir.service.implementation;

import by.audit.annotation.AuditAspect;
import by.audit.annotation.Loggable;
import by.vladimir.dto.CreateUserDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.dao.UserDao;
import by.vladimir.mapper.UserMapper;
import by.vladimir.service.UserService;
import by.vladimir.validator.CreateUserValidator;
import by.vladimir.validator.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Класс service ответственный за работу с пользователем.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final CreateUserValidator createUserValidator;

    private final UserMapper userMapper;


    @AuditAspect
    @Loggable
    @Override
    public User registration(CreateUserDto createUserDto) {
        ValidationResult validationResult = createUserValidator.isValid(createUserDto);
        if(!validationResult.isValid()){
            throw new IllegalArgumentException("Invalid CreateUserDto");
        }
        else {
            User user = userMapper.toUser(createUserDto);
            return userDao.save(user);
        }

    }

    @AuditAspect
    @Loggable
    @Override
    public Optional<User> authentication(String email) {
        Optional<User> userOptional = userDao.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional;
        } else {
            return Optional.empty();
        }
    }

    @AuditAspect
    @Loggable
    @Override
    public User findById(Long id){
        Optional<User> optionalUser = userDao.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        else {
            return null;
        }
    }

    @AuditAspect
    @Loggable
    @Override
    public boolean isUserExist(String email) {
        if (userDao.findByEmail(email).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @AuditAspect
    @Loggable
    @Override
    public List<User> userList() {
        return userDao.getListOfUsers();
    }

    @AuditAspect
    @Loggable
    @Override
    public List<UserDto> getAllUsersWithHabits() {
        List<UserDto> userDtos = userDao.getAllHabitsOfAllUsers();
        return userDtos;
    }

    @Loggable
    @AuditAspect
    @Override
    public void delete(Long id){
        if(userDao.findById(id).isPresent()){
            userDao.delete(id);
        }
        else {
            throw new NoSuchElementException("Пользователь не найден");
        }
    }
}