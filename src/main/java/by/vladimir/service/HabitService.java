package by.vladimir.service;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с привычками.
 */
public interface HabitService {

    /**
     * Получает на вход индификатор привычки и
     * возвращает по нему объект класса Habit.
     * @param id - индификатор привычки.
     * @return объект класса Habit.
     */
    Habit getHabitById(Long id);

    /**
     * Получает пользователя на вход.
     * По id пользователя проверяет есть ли у него список привычек.
     * Если списка нет возвращает новый список, если список есть, но он пустой
     * возвращает пустой список, если список есть и он не пустой возвращает список привычек.
     * @param user - пользователя список привычек, которого надо получить.
     * @return список привычек
     */
    List<Habit> getAll(User user);

    /**
     * Получает на вход createHabitDto.
     * Проверяет полученное Dto на валидность.
     * Если Dto валидно, мапит Dto в объект класса Habit
     * и передает в метод save класса HabitDao.
     * Если не валидно выдает исключение с сообщением.
     * @param createHabitDto - Dto получаемой привычки
     * @return Habit или исключение.
     */
    Habit create(CreateHabitDto createHabitDto);

    /**
     * Получает habitDto на вход.
     * Проверяет его на валидность.
     * Если Dto валидно, мапит Dto в объект класса Habit
     * и передает в метод update класса HabitDao.
     * Если не валидно выдает исключение с сообщением.
     * @param habitDto - Dto получаемой привычки
     */
    Habit update(HabitDto habitDto);

    /**
     * Получает id привычки.
     * Помещает в Optional результат выполения метода findById.
     * Проверяет что вернуд метод.
     * @param id - индификатор привычки, которую надо проверить на существование.
     * @return - возвращает true, если существует, false, если нет.
     */
    boolean containById(Long id);

    /**
     * Получает на вход id.
     * Передает id в метод delete, класса HabitDao.
     * @param id - индификатор привычки, которую надо удалить.
     */
    void delete(Long id);

}