//package by.vladimir.service;
//
//import by.vladimir.dao.HabitDao;
//import by.vladimir.dao.UserDao;
//import by.vladimir.dto.CreateHabitDto;
//import by.vladimir.dto.CreateUserDto;
//import by.vladimir.dto.HabitDto;
//import by.vladimir.entity.Frequency;
//import by.vladimir.entity.Habit;
//import by.vladimir.entity.Role;
//import by.vladimir.entity.User;
//import by.vladimir.utils.ConnectionManager;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//@Testcontainers
//public class HabitServiceTest {
//    @Container
//    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("habit_tracker")
//            .withUsername("vladimir")
//            .withPassword("admin");
//
//    private HabitService habitService;
//    private HabitDao habitDao;
//    private UserDao userDao;
//    private CreateHabitDto createHabitDto;
//
//    @BeforeEach
//     void setUp() throws SQLException {
//
//        habitDao = HabitDao.getInstance();
//        userDao =  UserDao.getInstance();
//        habitService = HabitService.getInstance();
//
//        try (Connection connection = ConnectionManager.get()) {
//            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.users (id SERIAL PRIMARY KEY);");
//            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.habits (id SERIAL PRIMARY KEY, name VARCHAR(255), user_id BIGINT REFERENCES users(id));");
//        }
//    }
//
//    @AfterEach
//     void tearDown() throws SQLException {
//        try (Connection connection = ConnectionManager.get()) {
//            connection.createStatement().execute("DELETE FROM main.habits;");
//            connection.createStatement().execute("DELETE FROM main.users;");
//
//        }
//    }
//
//    @Test
//     void testGetUserHabits() throws SQLException {
//
//        User user = new User(1L,"test@gmail.com","qwerty", Role.USER);
//        userDao.save(user);
//
//        Habit habit1 = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
//        Habit habit2 = new Habit(null, "Test Habit 2", "Description 2", Frequency.WEEKLY, user.getId());
//        habitDao.save(habit1);
//        habitDao.save(habit2);
//
//        List<Habit> habits = habitService.getUserHabits(user);
//        assertEquals(2, habits.size(), "Должно быть 2 привычки у пользователя.");
//
//    }
//
//    @Test
//     void testCreateHabit() throws SQLException {
//        User user = new User(1L,"test@gmail.com","qwerty", Role.USER);
//        userDao.save(user);
//
//        createHabitDto = new CreateHabitDto();
//        createHabitDto.setName("New Habit");
//        createHabitDto.setDescription("description");
//        createHabitDto.setFrequency(Frequency.DAILY.name());
//        createHabitDto.setUserId(user.getId());
//
//        habitService.createHabit(createHabitDto);
//
//        List<Habit> habits = habitService.getUserHabits(user);
//        assertEquals(1, habits.size(), "Должна быть 1 привычка у пользователя.");
//        assertEquals("New Habit", habits.get(0).getName(), "Имя привычки должно совпадать.");
//
//    }
//    @Test
//    void testUpdate() throws SQLException {
//        User user = new User(1L,"test@gmail.com","qwerty", Role.USER);
//        userDao.save(user);
//
//        Habit habit1 = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
//        habitDao.save(habit1);
//
//        HabitDto habitDto = new HabitDto();
//        habitDto.setId(habit1.getId());
//        habitDto.setName("Updated Habit");
//        habitDto.setDescription("desc");
//        habitDto.setFrequency(Frequency.WEEKLY);
//
//        habitService.update(habitDto);
//
//        Optional<Habit> updatedHabit = habitDao.findById(habit1.getId());
//        assertTrue(updatedHabit.isPresent(), "Привычка должна существовать.");
//        assertEquals("Updated Habit", updatedHabit.get().getName(), "Имя привычки должно быть обновлено.");
//    }
//    @Test
//     void testContainById() throws SQLException {
//        User user = new User(1L,"test@gmail.com","qwerty", Role.USER);
//        userDao.save(user);
//
//        Habit habit = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
//        habitDao.save(habit);
//
//        assertTrue(habitService.containById(habit.getId()), "Привычка должна существовать.");
//
//        assertFalse(habitService.containById(999L), "Привычка не должна существовать.");
//    }
//
//    @Test
//     void testDelete() throws SQLException {
//        User user = new User(1L,"test@gmail.com","qwerty", Role.USER);
//        userDao.save(user);
//
//        Habit habit = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
//        habitDao.save(habit);
//
//        habitService.delete(habit.getId());
//
//        assertFalse(habitService.containById(habit.getId()), "Привычка должна быть удалена.");
//    }
//
//}
