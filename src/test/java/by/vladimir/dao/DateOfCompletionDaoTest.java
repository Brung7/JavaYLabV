package by.vladimir.dao;

import by.vladimir.entity.*;
import by.vladimir.utils.ConnectionManager;
import by.vladimir.utils.DateFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class DateOfCompletionDaoTest {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("habit_tracker")
            .withUsername("vladimir")
            .withPassword("admin");

    private DateOfCompletionDao dateOfCompletionDao;
    private User user;
    private Habit habit;
    private UserDao userDao = UserDao.getInstance();
    private HabitDao habitDao = HabitDao.getInstance();
    private DateFormatter formatter = DateFormatter.getInstance();


    @BeforeEach
    public void setUp() throws SQLException {
        dateOfCompletionDao = DateOfCompletionDao.getInstance();
        try (Connection connection = ConnectionManager.get();) {

            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS main.users (id SERIAL PRIMARY KEY, email VARCHAR(255), password VARCHAR(255), role VARCHAR(50))");
            statement.execute();
            user = new User(1L,"test@gmail.com","qwerty", Role.USER);
            userDao.save(user);
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.habits (id SERIAL PRIMARY KEY, name VARCHAR(255), description VARCHAR(255), frequency VARCHAR(50), user_id BIGINT);");
            habit = new Habit(1L,"Gym","Description", Frequency.DAILY, user.getId());
            habitDao.save(habit);
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.dates (id SERIAL PRIMARY KEY, completion_date DATE, habit_id BIGINT);");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (Connection connection = ConnectionManager.get();) {
            connection.createStatement().execute("DELETE FROM main.dates;");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM main.users");
            statement.execute();
            connection.createStatement().execute("DELETE FROM main.habits;");
        }
    }

    @Test
    public void testSave() throws SQLException {
        DateOfCompletion dateToSave = new DateOfCompletion();
        dateToSave.setDate(new java.sql.Date(System.currentTimeMillis()));
        dateToSave.setHabitId(habit.getId());

        DateOfCompletion savedDate = dateOfCompletionDao.save(dateToSave);

        assertNotNull(savedDate.getId(), "ID сохраненного объекта не должен быть null.");

        try (Connection connection = ConnectionManager.get()) {
            {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM main.dates WHERE id = ?");
                statement.setLong(1, savedDate.getId());
                ResultSet resultSet = statement.executeQuery();
                assertTrue(resultSet.next(), "Запись должна существовать в базе данных.");
                Date expected = formatter.convertSqlToUtil(dateToSave.getDate());
                Date actual = formatter.convertSqlToUtil(resultSet.getDate("completion_date"));
                assertEquals(expected,actual, "Даты должны совпадать.");
                assertEquals(dateToSave.getHabitId(), resultSet.getLong("habit_id"), "ID привычки должен совпадать.");
            }
        }

    }
    @Test
    public void testDelete() throws SQLException {
        DateOfCompletion dateToSave = new DateOfCompletion();
        dateToSave.setDate(new java.sql.Date(System.currentTimeMillis()));
        dateToSave.setHabitId(habit.getId());
        DateOfCompletion savedDate = dateOfCompletionDao.save(dateToSave);

        dateOfCompletionDao.delete(savedDate.getId());

        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM main.dates WHERE id = ?");
            statement.setLong(1, savedDate.getId());
            ResultSet resultSet = statement.executeQuery();
            assertFalse(resultSet.next(), "Запись должна быть удалена из базы данных.");
        }

    }
    @Test
    public void testUpdate() throws SQLException {
        DateOfCompletion dateToSave = new DateOfCompletion();
        dateToSave.setDate(new java.sql.Date(System.currentTimeMillis()));
        dateToSave.setHabitId(habit.getId());
        DateOfCompletion savedDate = dateOfCompletionDao.save(dateToSave);

        savedDate.setDate(new java.sql.Date(System.currentTimeMillis() + 86400000));
        dateOfCompletionDao.update(savedDate);

        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM main.dates WHERE id = ?");
            statement.setLong(1, savedDate.getId());
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next(), "Запись должна существовать в базе данных.");
            Date expected = formatter.convertSqlToUtil(dateToSave.getDate());
            Date actual = formatter.convertSqlToUtil(resultSet.getDate("completion_date"));
            assertEquals(expected, actual, "Даты должны совпадать после обновления.");
        }
    }
    @Test
    void testFindByHabitId(){
        DateOfCompletion dateOfCompletion1 = new DateOfCompletion(null,habit.getId(),new java.sql.Date(System.currentTimeMillis()));
        dateOfCompletionDao.save(dateOfCompletion1);
        List<DateOfCompletion> list = dateOfCompletionDao.findByHabitId(habit.getId());
        assertEquals(1,list.size());
    }
    @Test
    void testFindById(){
        DateOfCompletion dateOfCompletion1 = new DateOfCompletion(null,habit.getId(),new java.sql.Date(System.currentTimeMillis()));
        DateOfCompletion date = dateOfCompletionDao.save(dateOfCompletion1);
        Optional<DateOfCompletion> optional = dateOfCompletionDao.findById(date.getId());
        assertTrue(optional.isPresent());
        Date expected = formatter.convertSqlToUtil(date.getDate());
        Date actual = formatter.convertSqlToUtil(optional.get().getDate());
        assertEquals(expected,actual);
    }
}
