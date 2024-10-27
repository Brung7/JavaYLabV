package by.vladimir.service;

import by.vladimir.dao.HabitDao;
import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.mapper.HabitMapper;
import by.vladimir.validator.CreateHabitValidator;
import by.vladimir.validator.UpdateHabitValidator;
import by.vladimir.validator.ValidationResult;

import java.util.*;

/**
 * Класс service ответственный за работу с привычками.
 */
public class HabitService {

    /**
     * Singleton для HabitService
     */
    private static final HabitService INSTANCE = new HabitService();

    /**
     * Сущность HabitDao
     */
    private final HabitDao habitDao = HabitDao.getInstance();

    /**
     * Сущность HabitMapper
     */
    private final HabitMapper habitMapper = HabitMapper.INSTANCE;

    /**
     * Сущность CreateHabitValidator
     */
    private final CreateHabitValidator habitValidator = CreateHabitValidator.getInstance();

    /**
     * Сущность UpdateHabitValidator
     */
    private final UpdateHabitValidator updaterHabitValidator = UpdateHabitValidator.getInstance();

    /**
     * Приватный конструктор HabitService
     */
    private HabitService() {
    }

    /**
     * Получает пользователя на вход.
     * По id пользователя проверяет есть ли у него список привычек.
     * Если списка нет возвращает новый список, если список есть, но он пустой
     * возвращает пустой список, если список есть и он не пустой возвращает список привычек.
     * @param user - пользователя список привычек, которого надо получить.
     * @return список привычек
     */
    public List<Habit> getUserHabits(User user) {
        Long id = user.getId();
        Optional<List<Habit>> optionalHabits = Optional.ofNullable(habitDao.findByUserId(id));
        if (optionalHabits.isPresent()) {
            if (!habitDao.findByUserId(id).isEmpty()) {
                return habitDao.findByUserId(id);
            } else {
                return Collections.emptyList();
            }
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Получает на вход createHabitDto.
     * Проверяет полученное Dto на валидность.
     * Если Dto валидно, мапит Dto в объект класса Habit
     * и передает в метод save класса HabitDao.
     * Если не валидно выдает исключение с сообщением.
     * @param createHabitDto - Dto получаемой привычки
     * @return Habit или исключение.
     */
    public Habit createHabit(CreateHabitDto createHabitDto) {
        ValidationResult validationResult = habitValidator.isValid(createHabitDto);
        if(!validationResult.isValid()){
            throw new IllegalArgumentException("Invalid CreateHabitDto");
        }
        else {
            Habit habit = habitMapper.toHabit(createHabitDto);
            return habitDao.save(habit);
        }
    }

    /**
     * Получает habitDto на вход.
     * Проверяет его на валидность.
     * Если Dto валидно, мапит Dto в объект класса Habit
     * и передает в метод update класса HabitDao.
     * Если не валидно выдает исключение с сообщением.
     * @param habitDto - Dto получаемой привычки
     */
    public void update(HabitDto habitDto) {
        ValidationResult validationResult = updaterHabitValidator.isValid(habitDto);
        if(!validationResult.isValid()){
            throw new IllegalArgumentException("Invalid habitDto");
        }
        else {
            Habit habit = habitMapper.toHabitFromHabitDto(habitDto);
            habitDao.update(habit);
        }
    }

    /**
     * Получает id привычки.
     * Помещает в Optional результат выполения метода findById.
     * Проверяет что вернуд метод.
     * @param id - индификатор привычки, которую надо проверить на существование.
     * @return - возвращает true, если существует, false, если нет.
     */
    public boolean containById(Long id) {
        Optional<Habit> habitOptional = habitDao.findById(id);
        if (habitOptional.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Получает на вход id.
     * Передает id в метод delete, класса HabitDao.
     * @param id - индификатор привычки, которую надо удалить.
     */
    public void delete(Long id) {
        habitDao.delete(id);
    }

    /**
     * Возвращает сущность HabitService.
     * @return сущность HabitService.
     */
    public static HabitService getInstance() {
        return INSTANCE;
    }
}