package by.vladimir.service;

import by.vladimir.dao.DateOfCompletionDao;
import by.vladimir.dao.HabitDao;
import by.vladimir.dao.UserDao;
import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.*;
import by.vladimir.mapper.DateOfComplMapper;
import by.vladimir.mapper.DateOfCompletionUpdateMapper;
import by.vladimir.utils.ConnectionManager;
import by.vladimir.utils.DateFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class DateOfCompletionServiceTest {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("habit_tracker")
            .withUsername("vladimir")
            .withPassword("admin");

    private UserDao userDao;
    private HabitDao habitDao;
    private DateOfComplMapper dateMapper;

    private DateOfCompletionDao dateOfCompletionDao;
    private DateOfCompletionUpdateMapper dateOfComplMapper;
    private DateFormatter formatter = DateFormatter.getInstance();
    private DateOfCompletionService dateService;


    @BeforeEach
    void setUp() throws SQLException {

        habitDao = HabitDao.getInstance();
        userDao = UserDao.getInstance();
        dateMapper = DateOfComplMapper.getInstance();
        dateOfCompletionDao = DateOfCompletionDao.getInstance();
        dateOfComplMapper = DateOfCompletionUpdateMapper.getInstance();
        dateService = DateOfCompletionService.getInstance();


        try (Connection connection = ConnectionManager.get()) {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.users (id SERIAL PRIMARY KEY);");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.habits (id SERIAL PRIMARY KEY, name VARCHAR(255), user_id BIGINT REFERENCES users(id));");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.dates (id SERIAL PRIMARY KEY,habit_id BIGINT REFERENCES habits(id),completion_date DATE);");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            connection.createStatement().execute("DELETE FROM main.habits;");
            connection.createStatement().execute("DELETE FROM main.users;");
            connection.createStatement().execute("DELETE FROM main.dates;");

        }
    }

    @Test
    void createDateOfCompletion() {
        User user = new User(1L, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);

        Habit habit1 = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
        habitDao.save(habit1);
        CreateDateOfComplDto createDateOfComplDto = CreateDateOfComplDto.builder()
                .id(null)
                .habitId(habit1.getId())
                .date("2024-10-19")
                .build();
        DateOfCompletion date = dateMapper.mapFrom(createDateOfComplDto);
        dateOfCompletionDao.save(date);
        List<DateOfCompletion> dateOfCompletionList = dateOfCompletionDao.findByHabitId(habit1.getId());
        Assertions.assertEquals(1, dateOfCompletionList.size());
    }

    @Test
    void update() {
        User user = new User(1L, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);

        Habit habit1 = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
        habitDao.save(habit1);
        CreateDateOfComplDto createDateOfComplDto = CreateDateOfComplDto.builder()
                .habitId(habit1.getId())
                .date("2024-10-21")
                .build();
        DateOfCompletion date = dateMapper.mapFrom(createDateOfComplDto);
        dateOfCompletionDao.save(date);
        DateOfCompletionDto dateOfCompletionDto = DateOfCompletionDto.
                builder()
                .id(date.getId())
                .date("2024-10-18")
                .build();
        DateOfCompletion dateUpdate = dateOfComplMapper.mapFrom(dateOfCompletionDto);
        dateOfCompletionDao.update(dateUpdate);
        Optional<DateOfCompletion> optional = dateOfCompletionDao.findById(dateUpdate.getId());
        Assertions.assertTrue(optional.isPresent());
        Date expected = formatter.convertSqlToUtil(dateUpdate.getDate());
        Date actual = formatter.convertSqlToUtil(optional.get().getDate());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        User user = new User(1L, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);

        Habit habit1 = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
        habitDao.save(habit1);
        CreateDateOfComplDto createDateOfComplDto = CreateDateOfComplDto.builder()
                .id(null)
                .habitId(habit1.getId())
                .date("2024-10-19")
                .build();
        DateOfCompletion date = dateMapper.mapFrom(createDateOfComplDto);
        dateOfCompletionDao.save(date);
        dateOfCompletionDao.delete(date.getId());
        Assertions.assertFalse(dateService.containById(date.getId()));
    }

    @Test
    void findByHabitId() {
        User user = new User(1L, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);

        Habit habit1 = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
        habitDao.save(habit1);
        CreateDateOfComplDto createDateOfComplDto = CreateDateOfComplDto.builder()
                .id(null)
                .habitId(habit1.getId())
                .date("2024-10-19")
                .build();
        DateOfCompletion date = dateMapper.mapFrom(createDateOfComplDto);
        dateOfCompletionDao.save(date);
        List<DateOfCompletion> list = dateService.findByHabitId(habit1.getId());
        Assertions.assertEquals(1, list.size());
    }

    @Test
    void containById() {
        User user = new User(1L, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);

        Habit habit1 = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
        habitDao.save(habit1);
        CreateDateOfComplDto createDateOfComplDto = CreateDateOfComplDto.builder()
                .id(null)
                .habitId(habit1.getId())
                .date("2024-10-19")
                .build();
        DateOfCompletion date = dateMapper.mapFrom(createDateOfComplDto);
        dateOfCompletionDao.save(date);
        Assertions.assertTrue(dateService.containById(date.getId()));
    }
}
