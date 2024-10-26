package by.vladimir.dao;
import by.vladimir.dto.HabitDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.utils.ConnectionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
public class UserDaoTest {
    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("habit_tracker")
            .withUsername("vladimir")
            .withPassword("admin");

    private UserDao userDao;

    @BeforeEach
    public void setUp() throws SQLException {
        ConnectionManager.get();

        userDao = UserDao.getInstance();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS main.users (id SERIAL PRIMARY KEY, email VARCHAR(255), password VARCHAR(255), role VARCHAR(50))")) {
            statement.execute();
        }
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(Role.USER);

        User savedUser = userDao.save(user);

        Assertions.assertNotNull(savedUser.getId());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getRole(), savedUser.getRole());
    }
    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(Role.USER);
        User savedUser = userDao.save(user);
        userDao.update(savedUser);
        Assertions.assertNotNull(savedUser.getId());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getRole(), savedUser.getRole());
    }
    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(Role.USER);
        User savedUser = userDao.save(user);
        Long id = savedUser.getId();
        userDao.delete(id);
        try (Connection connection = ConnectionManager.get()) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM main.users WHERE id = " + 6L + ";");
            assertFalse(resultSet.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testFindByEmail_UserExists() throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            connection.createStatement().execute("INSERT INTO main.users (email, password, role) VALUES ('test@example.com', 'password123', 'USER');");
        }

        Optional<User> user = userDao.findByEmail("test@example.com");

        assertTrue(user.isPresent(), "Пользователь должен быть найден.");
        assertEquals("test@example.com", user.get().getEmail(), "Email должен совпадать.");
    }

    @Test
    public void testFindByEmail_UserDoesNotExist() {
        Optional<User> user = userDao.findByEmail("nonexistent@example.com");

        assertFalse(user.isPresent(), "Пользователь не должен быть найден.");
    }


    @Test
    public void testGetAllHabitsOfAllUsers() throws SQLException {
        Connection connection = ConnectionManager.get();

        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO main.users (id, email,password,role) VALUES (1, 'test1@example.com','qwerty','USER')");
        insertStatement.executeUpdate();
        insertStatement = connection.prepareStatement("INSERT INTO main.habits (user_id, id, name, description, frequency) VALUES (1, 1, 'Exercise', 'Daily exercise routine', 'DAILY')," +
                "(1, 2, 'Meditation', 'Daily meditation practice', 'DAILY')");
        insertStatement.executeUpdate();

        UserDao userDao = UserDao.getInstance();

        List<UserDto> users = userDao.getAllHabitsOfAllUsers();


        assertEquals(1, users.size());


        UserDto userDto = users.get(0);
        assertEquals(1L, userDto.getId().longValue());
        assertEquals("test1@example.com", userDto.getEmail());
        assertEquals(2, userDto.getHabitList().size());


        HabitDto habit1 = userDto.getHabitList().get(0);
        assertEquals(1L, habit1.getId().longValue());
        assertEquals("Exercise", habit1.getName());
        assertEquals("Daily exercise routine", habit1.getDescription());
        assertEquals(Frequency.DAILY, habit1.getFrequency());

        HabitDto habit2 = userDto.getHabitList().get(1);
        assertEquals(2L, habit2.getId().longValue());
        assertEquals("Meditation", habit2.getName());
        assertEquals("Daily meditation practice", habit2.getDescription());
        assertEquals(Frequency.DAILY, habit2.getFrequency());
    }




        @AfterEach
    public void tearDown() throws SQLException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM main.users")) {
            statement.execute();
        }
    }

}
