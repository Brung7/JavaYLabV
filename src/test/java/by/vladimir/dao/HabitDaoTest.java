//package by.vladimir.dao;
//
//import by.vladimir.entity.Frequency;
//import by.vladimir.entity.Habit;
//import by.vladimir.entity.Role;
//import by.vladimir.entity.User;
//import by.vladimir.utils.ConnectionManager;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Testcontainers
//public class HabitDaoTest {
//    @Container
//    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("habit_tracker")
//            .withUsername("vladimir")
//            .withPassword("admin");
//
//    private HabitDao habitDao;
//    private UserDao userDao;
//    private User user;
//
//    @BeforeEach
//    public void setUp() throws SQLException {
//        habitDao = HabitDao.getInstance();
//        userDao = UserDao.getInstance();
//        try (Connection connection = ConnectionManager.get()
//               ) {
//            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS main.users (id SERIAL PRIMARY KEY, email VARCHAR(255), password VARCHAR(255), role VARCHAR(50))");
//                statement.execute();
//                user = new User(1L,"test@gmail.com","qwerty", Role.USER);
//                userDao.save(user);
//            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.habits (id SERIAL PRIMARY KEY, name VARCHAR(255), description VARCHAR(255), frequency VARCHAR(50), user_id BIGINT);");
//        }
//    }
//
//
//
//    @Test
//    public void testSave() throws SQLException {
//        Habit habit = new Habit(null, "Test Habit", "Description", Frequency.DAILY, user.getId());
//        Habit savedHabit = habitDao.save(habit);
//
//        assertNotNull(savedHabit.getId(), "ID должен быть сгенерирован при сохранении.");
//        assertEquals(habit.getName(), savedHabit.getName());
//        assertEquals(habit.getDescription(), savedHabit.getDescription());
//        assertEquals(habit.getFrequency(), savedHabit.getFrequency());
//        assertEquals(habit.getUserId(), savedHabit.getUserId());
//    }
//
//    @Test
//    public void testDelete() throws SQLException {
//        Habit habit = new Habit(null, "Test Habit", "Description", Frequency.DAILY, user.getId());
//        Habit savedHabit = habitDao.save(habit);
//
//        habitDao.delete(savedHabit.getId());
//
//        Optional<Habit> deletedHabit = habitDao.findById(savedHabit.getId());
//        assertFalse(deletedHabit.isPresent(), "Пользователь должен быть удален.");
//    }
//
//    @Test
//    public void testFindById() throws SQLException {
//        Habit habit = new Habit(null, "Test Habit", "Description", Frequency.DAILY, user.getId());
//        Habit savedHabit = habitDao.save(habit);
//
//        Optional<Habit> foundHabit = habitDao.findById(savedHabit.getId());
//        assertTrue(foundHabit.isPresent(), "Должен быть найден сохраненный привычка.");
//        assertEquals(savedHabit.getName(), foundHabit.get().getName());
//    }
//    @Test
//    public void testUpdate() throws SQLException {
//        Habit habit = new Habit(null, "Test Habit", "Description", Frequency.DAILY, user.getId());
//        Habit savedHabit = habitDao.save(habit);
//
//        savedHabit.setName("Updated Habit");
//        habitDao.update(savedHabit);
//
//        Optional<Habit> updatedHabit = habitDao.findById(savedHabit.getId());
//        assertTrue(updatedHabit.isPresent(), "Должен быть найден обновленный привычка.");
//        assertEquals("Updated Habit", updatedHabit.get().getName());
//    }
//    @Test
//    public void testFindByUserId() throws SQLException {
//        Habit habit1 = new Habit(null, "Test Habit 1", "Description 1", Frequency.DAILY, user.getId());
//        Habit habit2 = new Habit(null, "Test Habit 2", "Description 2", Frequency.WEEKLY, user.getId());
//        habitDao.save(habit1);
//        habitDao.save(habit2);
//
//        List<Habit> habits = habitDao.findByUserId(user.getId());
//
//        assertEquals(2, habits.size(), "Должно быть найдено две привычки для пользователя с ID 1.");
//    }
//
//    @AfterEach
//    public void tearDown() throws SQLException {
//        try (Connection connection = ConnectionManager.get()) {
//            userDao.delete(user.getId());
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM main.users");
//                statement.execute();
//            connection.createStatement().execute("DELETE FROM main.habits;");
//        }
//    }
//
//
//
//}
