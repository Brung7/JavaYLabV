package by.vladimir.service;

import by.vladimir.dto.CreateUserDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Сервис дял работы с пользователем.
 */
public interface UserService {

    /**
     * Получает на вход пользователя.
     * Проверяет его поля на валидность.
     * Если проверку прошел мапит CreateUserDto в объект класса User
     * и передает его в метод save.
     * @param createUserDto - объект получаемого пользователя.
     * @return возвращает результат выполнения метод save из UserDao.
     */
    User registration(CreateUserDto createUserDto);

    /**
     * Получает на вход email.
     * Передает email в метод findByEmail и
     * результат выполнения метода помещает в Optional.
     * Проверяет, что вернул метод findByEmail.
     * @param email - строка c email пользователя.
     * @return возвращает Optional полученного пользователя.
     */
    Optional<User> authentication(String email);

    /**
     * Врзвращает пользователя по id
     * @param id
     * @return объект класса User
     */
    User findById(Long id);

    /**
     * Получает на вход email пользователя.
     * Проверяет существует ли пользователь с таким email.
     * @param email - строка c email пользователя.
     * @return возвращает true если пользователь есть, false если пользователя нет.
     */
    boolean isUserExist(String email);

    /**
     * Возвращает лист пользователей со всеми их привычками.
     * @return лист пользователей со всеми их привычками.
     */
    List<UserDto> getAllUsersWithHabits();

    /**
     * @return возвращает лист пользователей, полученный из метода userDao.getListOfUsers().
     */
    List<User> userList();

    /**
     * Метод для удаления пользователя.
     */
    void delete(Long id);

}