package by.vladimir.controller;


import by.vladimir.dao.HabitDao;
import by.vladimir.dao.UserDao;
import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.service.HabitService;
import by.vladimir.utils.ConnectionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@Testcontainers
public class HabitControllerTest {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("habit_tracker")
            .withUsername("vladimir")
            .withPassword("admin");

    private HabitService habitService;
    private UserDao userDao;
    private HabitDao habitDao;

    @BeforeEach
    public void setUp() throws SQLException {

        habitDao = HabitDao.getInstance();
        userDao = UserDao.getInstance();
        habitService = HabitService.getInstance();

        try (Connection connection = ConnectionManager.get()) {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.users (id SERIAL PRIMARY KEY);");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.habits (id SERIAL PRIMARY KEY, name VARCHAR(255), description TEXT, frequency VARCHAR(50), user_id BIGINT REFERENCES main.users(id));");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            connection.createStatement().execute("DELETE FROM main.habits;");
            connection.createStatement().execute("DELETE FROM main.users;");
        }
    }

    @Test
    public void testCreateHabitToUser() throws SQLException {
        User user = new User(1L, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);

        CreateHabitDto createHabitDto = CreateHabitDto.builder()
                .name("Test Habit")
                .description("Description of test habit")
                .frequency("DAILY")
                .userId(user.getId())
                .build();

        habitService.createHabit(createHabitDto);

        List<Habit> habits = habitService.getUserHabits(user);
        assertEquals(1, habits.size(), "Должна быть 1 привычка для пользователя.");
        assertEquals("Test Habit", habits.get(0).getName(), "Название привычки должно совпадать.");
    }

    @Test
    public void testUpdateHabit() throws SQLException {
        User user = new User(1L, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);

        CreateHabitDto createHabitDto = CreateHabitDto.builder()
                .name("Old Habit")
                .description("Old description")
                .frequency("WEEKLY")
                .userId(user.getId())
                .build();

        Habit habit = habitService.createHabit(createHabitDto);

        HabitDto habitDto = HabitDto.builder()
                .id(habit.getId())
                .name("Updated Habit")
                .description("Updated description")
                .frequency(Frequency.DAILY)
                .build();

        habitService.update(habitDto);

        List<Habit> habits = habitService.getUserHabits(user);
        assertEquals(1, habits.size(), "Должна быть 1 привычка для пользователя.");
        assertEquals("Updated Habit", habits.get(0).getName(), "Название привычки должно быть обновлено.");
    }

    @Test
    public void testDeleteHabit() throws SQLException {
        User user = new User(1L, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);

        CreateHabitDto createHabitDto = CreateHabitDto.builder()
                .name("Habit to Delete")
                .description("This habit will be deleted.")
                .frequency("MONTHLY")
                .userId(user.getId())
                .build();

        Habit habit = habitService.createHabit(createHabitDto);

        habitService.delete(habit.getId());
        List<Habit> habits = habitService.getUserHabits(user);
        assertTrue(habits.isEmpty(), "Список привычек должен быть пустым.");
    }

    @Test
    public void testGetAllUserHabits() throws SQLException {
        User user = new User(1L, "test@gmail.com", "qwerty", Role.USER);
        userDao.save(user);

        CreateHabitDto createHabitDto1 = CreateHabitDto.builder()
                .name("First Habit")
                .description("Description of first habit")
                .frequency("DAILY")
                .userId(user.getId())
                .build();

        CreateHabitDto createHabitDto2 = CreateHabitDto.builder()
                .name("Second Habit")
                .description("Description of second habit")
                .frequency("WEEKLY")
                .userId(user.getId())
                .build();

        habitService.createHabit(createHabitDto1);
        habitService.createHabit(createHabitDto2);

        List<Habit> habits = habitService.getUserHabits(user);
        assertEquals(2, habits.size(), "Должно быть 2 привычки для пользователя.");
    }

}
