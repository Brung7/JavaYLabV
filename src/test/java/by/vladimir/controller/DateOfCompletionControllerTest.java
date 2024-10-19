package by.vladimir.controller;

import by.vladimir.dao.HabitDao;
import by.vladimir.dao.UserDao;
import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.*;
import by.vladimir.mapper.DateOfComplMapper;
import by.vladimir.mapper.DateOfCompletionUpdateMapper;
import by.vladimir.service.DateOfCompletionService;
import by.vladimir.service.HabitService;
import by.vladimir.utils.ConnectionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@Testcontainers
public class DateOfCompletionControllerTest {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("habit_tracker")
            .withUsername("vladimir")
            .withPassword("admin");

    private HabitService habitService;
    private DateOfCompletionService dateOfCompletionService;
    private UserDao userDao;
    private HabitDao habitDao;
    private DateOfCompletionUpdateMapper dateOfComplMapper;
    private DateOfComplMapper dateMapper;
    private DateOfCompletion dateOfCompletion;
    @BeforeEach
    public void setUp() throws SQLException {
        habitDao = HabitDao.getInstance();
        userDao = UserDao.getInstance();
        dateOfCompletionService = DateOfCompletionService.getInstance();
        habitService = HabitService.getInstance();
        dateOfComplMapper = DateOfCompletionUpdateMapper.getInstance();
        dateMapper = DateOfComplMapper.getInstance();
        dateOfCompletion = new DateOfCompletion();

        try (Connection connection = ConnectionManager.get()) {
            connection.createStatement().execute("CREATE SCHEMA IF NOT EXISTS main");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.users (id SERIAL PRIMARY KEY,email varchar(255),password varchar(255),role varchar(10));");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.habits (id SERIAL PRIMARY KEY, name VARCHAR(255), user_id BIGINT REFERENCES main.users(id));");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.dates (id SERIAL PRIMARY KEY, completion_date DATE, habit_id BIGINT REFERENCES main.habits(id));");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            connection.createStatement().execute("DELETE FROM main.dates;");
            connection.createStatement().execute("DELETE FROM main.habits;");
            connection.createStatement().execute("DELETE FROM main.users;");
        }
    }

    @Test
    public void testCreateDateToHabit() throws SQLException {
        User user = new User(1L, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);

        Habit habit = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
        habitDao.save(habit);

        CreateDateOfComplDto dateDto = CreateDateOfComplDto.builder()
                .date("2024-10-01")
                .habitId(habit.getId())
                .build();

        dateOfCompletionService.createDateOfCompletion(dateDto);

        List<DateOfCompletion> dates = dateOfCompletionService.findByHabitId(habit.getId());
        assertEquals(1, dates.size(), "Должна быть 1 дата выполнения для привычки.");
        assertEquals("2024-10-01", dates.get(0).getDate().toString(), "Дата должна совпадать.");
    }

    @Test
    public void testUpdateDate() throws SQLException {
        User user = new User(1L, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);

        Habit habit = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
        habitDao.save(habit);

        CreateDateOfComplDto dateDto = CreateDateOfComplDto.builder()
                .id(1L)
                .date("2024-10-01")
                .habitId(habit.getId())
                .build();

        DateOfCompletion date = dateOfCompletionService.createDateOfCompletion(dateDto);
        dateOfCompletion = dateMapper.mapFrom(dateDto);

        DateOfCompletionDto dateUpdateDto = DateOfCompletionDto.builder()
                .id(date.getId())
                .date("2024-10-02")
                .build();

        dateOfCompletionService.update(dateUpdateDto);

        List<DateOfCompletion> dates = dateOfCompletionService.findByHabitId(habit.getId());
        assertEquals(1, dates.size(), "Должна быть 1 дата выполнения для привычки.");
        assertEquals("2024-10-02", dates.get(0).getDate().toString(), "Дата должна быть обновлена.");
    }

    @Test
    public void testDeleteDate() throws SQLException {
        User user = new User(null, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);
        Habit habit = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
        habitDao.save(habit);

        CreateDateOfComplDto dateDto = CreateDateOfComplDto.builder()
                .date("2024-10-01")
                .habitId(habit.getId())
                .build();

       DateOfCompletion date = dateOfCompletionService.createDateOfCompletion(dateDto);
        dateOfCompletionService.delete(date.getId());
        List<DateOfCompletion> dates = dateOfCompletionService.findByHabitId(habit.getId());
        assertTrue(dates.isEmpty(), "Список дат выполнения должен быть пустым.");
    }
}
