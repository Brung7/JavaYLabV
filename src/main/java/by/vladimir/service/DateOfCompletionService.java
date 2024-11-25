package by.vladimir.service;

import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.DateOfCompletion;

import java.util.List;

/**
 * Сервис для работы с датой выполнения привычек.
 */
public interface DateOfCompletionService {

    /**
     * Получает на вход CreateDateOfComplDto.
     * Если Dto валидно, мапит Dto в объект класса DateOfCompletion
     * и передает в метод save класса DateOfCompletionDao.
     * Если не валидно выдает исключение с сообщением.
     * @param createDateOfComplDto - объект получаемой даты.
     * @return объект класса DateOfCompletion или исключение.
     */
    DateOfCompletion create(CreateDateOfComplDto createDateOfComplDto);

    /**
     * Получает на вход CreateDateOfComplDto.
     * Если Dto валидно, мапит Dto в объект класса DateOfCompletion
     * и передает в метод update класса DateOfCompletionDao.
     * Если не валидно выдает исключение с сообщением.
     * @param dateOfComplDto - объект получаемой даты.
     * @return объект класса DateOfCompletion или исключение.
     */
    void update(DateOfCompletionDto dateOfComplDto);

    /**
     * Получает id даты.
     * Передает id в метод delete класса DateOfCompletionDao.
     * @param id - индификатор даты, которую надо удалить.
     */
    void delete(Long id);

    /**
     * Получает на вход id паривычки.
     * Передает id в метод findByHabitId класса DateOfCompletionDao.
     * Проверяет существует ли список дат у привычки с заданной id.
     * Если существует возвращает список, если нет, создает новый и возвращает его.
     * @param habitId - индификатор привычки, список дат, которой надо поулчить
     * @return список дат.
     */
    List<DateOfCompletion> findByHabitId(Long habitId);

    /**
     * Получает на вход id даты.
     * Обрабатывает полученный результат из метода findById
     * класса DateOfCompletionDao.
     * Возвращает true если дата с таким id существует, false если нет.
     * @param id - индификатор даты.
     * @return true или false.
     */
    boolean containById(Long id);
}