package by.vladimir.service;

import by.vladimir.dao.UserDao;
import by.vladimir.dto.CreateUserDto;
import by.vladimir.entity.User;
import by.vladimir.utils.ConnectionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
public class UserServiceTest {
    private CreateUserDto createUserDto;
    private UserDao userDao = UserDao.getInstance();
    private UserService userService = UserService.getInstance();
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("habit_tracker")
            .withUsername("vladimir")
            .withPassword("admin");
    @BeforeEach
    void setUp() throws SQLException {

        userDao = UserDao.getInstance();

        try (Connection connection = ConnectionManager.get()) {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.users (id SERIAL PRIMARY KEY,email varchar(255),password varchar(255),role varchar(10));");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            connection.createStatement().execute("DELETE FROM main.users;");
        }
    }
    @Test
    public void testRegistration() {
        createUserDto = new CreateUserDto("test@example.com", "password123", "USER");
        User registeredUser = userService.registration(createUserDto);
        assertNotNull(registeredUser);
    }
    @Test
    public void testAuthentication() {
        createUserDto = new CreateUserDto("test@example.com", "password123", "USER");
        userService.registration(createUserDto);
        String email = "test@example.com";
        Optional<User> authenticatedUser = userService.authentication(email);
        assertTrue(authenticatedUser.isPresent());
    }
    @Test
    public void testIsUserExist() {
        createUserDto = new CreateUserDto("test@example.com", "password123", "USER");
        userService.registration(createUserDto);
        String email = "test@example.com";
        boolean userExists = userService.isUserExist(email);
        assertTrue(userExists);
    }
    @Test
    public void testUserList() {
        createUserDto = new CreateUserDto("test@example.com", "password123", "USER");
        userService.registration(createUserDto);
        createUserDto = new CreateUserDto("vladimir@example.com", "password123", "USER");
        userService.registration(createUserDto);
        List<User> userList = userService.userList();
        Assertions.assertEquals(2,userList.size());
    }
}
