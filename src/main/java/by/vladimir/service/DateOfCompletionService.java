package by.vladimir.service;

import by.vladimir.dao.DateOfCompletionDao;
import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.mapper.DateOfCompletionMapper;
import by.vladimir.validator.CreateDateValidator;
import by.vladimir.validator.UpdateDateValidator;
import by.vladimir.validator.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Класс service ответственный за работу с датами выполнения.
 */
@Service
public class DateOfCompletionService {
    private final DateOfCompletionDao dateDao;
    private final CreateDateValidator createDateValidator;
    private final DateOfCompletionMapper dateMapper;
    private final UpdateDateValidator updateDateValidator;


    @Autowired
    public DateOfCompletionService(DateOfCompletionDao dateDao, CreateDateValidator createDateValidator, DateOfCompletionMapper dateMapper,
                                   UpdateDateValidator updateDateValidator) {
        this.dateDao = dateDao;
        this.createDateValidator = createDateValidator;
        this.dateMapper = dateMapper;
        this.updateDateValidator = updateDateValidator;
    }

    /**
     * Получает на вход CreateDateOfComplDto.
     * Если Dto валидно, мапит Dto в объект класса DateOfCompletion
     * и передает в метод save класса DateOfCompletionDao.
     * Если не валидно выдает исключение с сообщением.
     * @param createDateOfComplDto - объект получаемой даты.
     * @return объект класса DateOfCompletion или исключение.
     */
    public DateOfCompletion createDateOfCompletion(CreateDateOfComplDto createDateOfComplDto) {
        ValidationResult validationResult = createDateValidator.isValid(createDateOfComplDto);
        if(!validationResult.isValid()){
            throw new IllegalArgumentException("Invalid CreateDateOfComplDto");
        }
        else {
            DateOfCompletion date = dateMapper.toDateOfCompl(createDateOfComplDto);
            return dateDao.save(date);
        }
    }

    /**
     * Получает на вход CreateDateOfComplDto.
     * Если Dto валидно, мапит Dto в объект класса DateOfCompletion
     * и передает в метод update класса DateOfCompletionDao.
     * Если не валидно выдает исключение с сообщением.
     * @param dateOfComplDto - объект получаемой даты.
     * @return объект класса DateOfCompletion или исключение.
     */
    public void update(DateOfCompletionDto dateOfComplDto) {
        ValidationResult validationResult = updateDateValidator.isValid(dateOfComplDto);
        if(!validationResult.isValid()){
            throw new IllegalArgumentException("Invalid DateOfCompletionDto");
        }
        else {
            DateOfCompletion date = dateMapper.toDateOfComplFromDto(dateOfComplDto);
            dateDao.update(date);
        }
    }

    /**
     * Получает id даты.
     * Передает id в метод delete класса DateOfCompletionDao.
     * @param id - индификатор даты, которую надо удалить.
     */
    public void delete(Long id) {
        dateDao.delete(id);
    }

    /**
     * Получает на вход id паривычки.
     * Передает id в метод findByHabitId класса DateOfCompletionDao.
     * Проверяет существует ли список дат у привычки с заданной id.
     * Если существует возвращает список, если нет, создает новый и возвращает его.
     * @param habitId - индификатор привычки, список дат, которой надо поулчить
     * @return список дат.
     */
    public List<DateOfCompletion> findByHabitId(Long habitId) {
        List<DateOfCompletion> dateOfCompletionList = dateDao.findByHabitId(habitId);
        Optional<List<DateOfCompletion>> dateOfCompletionOptional = Optional.ofNullable(dateOfCompletionList);
        if (dateOfCompletionOptional.isPresent()) {
            if (!dateOfCompletionList.isEmpty()) {
                return dateOfCompletionList;
            } else {
                return Collections.emptyList();
            }

        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Получает на вход id даты.
     * Обрабатывает полученный результат из метода findById
     * класса DateOfCompletionDao.
     * Возвращает true если дата с таким id существует, false если нет.
     * @param id - индификатор даты.
     * @return true или false.
     */
    public boolean containById(Long id) {
        Optional<DateOfCompletion> date = dateDao.findById(id);
        return date.isPresent();
    }

}