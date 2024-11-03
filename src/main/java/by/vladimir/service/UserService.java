package by.vladimir.service;

import by.vladimir.dto.CreateUserDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.User;
import by.vladimir.dao.UserDao;
import by.vladimir.mapper.UserMapper;
import by.vladimir.validator.CreateUserValidator;
import by.vladimir.validator.ValidationResult;

import java.util.List;
import java.util.Optional;

/**
 * Класс service ответственный за работу с пользователем.
 */
public class UserService {

    /**
     * Singleton для UserService.
     */
    private static final UserService INSTANCE = new UserService();

    /**
     * Приватный конструктор.
     */
    private UserService() {
    }

    /**
     * Сущность userDao.
     */
    UserDao userDao = UserDao.getInstance();
    /**
     * Сущность createUserValidator.
     */
    CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    /**
     * Сущность userMapper.
     */
    UserMapper userMapper = UserMapper.INSTANCE;

    /**
     * Получает на вход пользователя.
     * Проверяет его поля на валидность.
     * Если проверку прошел мапит CreateUserDto в объект класса User
     * и передает его в метод save.
     * @param createUserDto - объект получаемого пользователя.
     * @return возвращает результат выполнения метод save из UserDao.
     */
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

    /**
     * Получает на вход email.
     * Передает email в метод findByEmail и
     * результат выполнения метода помещает в Optional.
     * Проверяет, что вернул метод findByEmail.
     * @param email - строка c email пользователя.
     * @return возвращает Optional полученного пользователя.
     */
    public Optional<User> authentication(String email) {
        Optional<User> userOptional = userDao.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional;
        } else {
            return Optional.empty();
        }
    }

    /**
     * Получает на вход email пользователя.
     * Проверяет существует ли пользователь с таким email.
     * @param email - строка c email пользователя.
     * @return возвращает true если пользователь есть, false если пользователя нет.
     */
    public boolean isUserExist(String email) {
        if (userDao.findByEmail(email).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return возвращает лист пользователей, полученный из метода userDao.getListOfUsers().
     */
    public List<User> userList() {
        return userDao.getListOfUsers();
    }

    /**
     * Возвращает лист пользователей со всеми их привычками.
     * @return лист пользователей со всеми их привычками.
     */
    public List<UserDto> getAllUsersWithHabits() {
        List<UserDto> userDtos = userDao.getAllHabitsOfAllUsers();
        return userDtos;
    }

    /**
     * Возвращает сущность UserService
     * @return сущность UserService
     */
    public static UserService getInstance(){
        return INSTANCE;
    }
}