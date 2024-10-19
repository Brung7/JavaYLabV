package by.vladimir.controller;

import by.vladimir.dao.UserDao;
import by.vladimir.dto.CreateUserDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.service.HabitService;
import by.vladimir.service.UserService;
import by.vladimir.utils.Command;
import by.vladimir.utils.ConnectionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserControllerTest {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("habit_tracker")
            .withUsername("vladimir")
            .withPassword("admin");

    private UserService userService;
    private HabitService habitService;

    @BeforeEach
    public void setUp() throws SQLException {
        userService = UserService.getINSTANCE();
        habitService = HabitService.getInstance();

        try (Connection connection = ConnectionManager.get()) {
            connection.createStatement().execute("CREATE SCHEMA IF NOT EXISTS main");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.users (id SERIAL PRIMARY KEY, email VARCHAR(255) UNIQUE, password VARCHAR(255), role VARCHAR(50));");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS main.habits (id SERIAL PRIMARY KEY, name VARCHAR(255), description varchar(255), frequency VARCHAR(50), user_id BIGINT REFERENCES main.users(id));");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            connection.createStatement().execute("DELETE FROM main.users;");
            connection.createStatement().execute("DELETE FROM main.habits;");
        }
    }

    @Test
    public void testRegistration() {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .email("test@example.com")
                .password("password")
                .role("USER")
                .build();

        assertFalse(userService.isUserExist(createUserDto.getEmail()), "Пользователь должен отсутствовать перед регистрацией.");

        User user = userService.registration(createUserDto);

        assertTrue(userService.isUserExist(user.getEmail()), "Пользователь должен существовать после регистрации.");
    }

    @Test
    public void testAuthenticationSuccess() {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .email("test@example.com")
                .password("password")
                .role("USER")
                .build();

        User user = userService.registration(createUserDto);

        Optional<User> userOptional = userService.authentication(createUserDto.getEmail());
        assertTrue(userOptional.isPresent(), "Пользователь должен быть найден.");

        user = userOptional.get();
        assertEquals(createUserDto.getPassword(), user.getPassword(), "Пароль должен совпадать.");
    }

    @Test
    public void testAuthenticationFailure() {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .email("test@example.com")
                .password("password123")
                .role("USER")
                .build();

        User user = userService.registration(createUserDto);

        Optional<User> userOptional = userService.authentication(createUserDto.getEmail());
        assertTrue(userOptional.isPresent(), "Пользователь должен быть найден.");

        assertNotEquals("wrongPassword", userOptional.get().getPassword(), "Пароль не должен совпадать.");
    }

    @Test
    public void testShowAllUsers() {
        CreateUserDto user1 = CreateUserDto.builder()
                .email("user1@example.com")
                .password("passwordasda")
                .role("USER")
                .build();

        CreateUserDto user2 = CreateUserDto.builder()
                .email("user2@example.com")
                .password("passwordadada")
                .role("ADMIN")
                .build();

        User user = userService.registration(user1);
        User user3 =userService.registration(user2);

        List<User> users = userService.userList();
        assertEquals(2, users.size(), "Должно быть 2 пользователя.");
    }

    @Test
    public void testShowAllHabitsAllUsers() {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .email("user@example.com")
                .password("password")
                .role("USER")
                .build();

        User user = userService.registration(createUserDto);


        List<UserDto> usersWithHabits = userService.getAllUsersWithHabits();

        assertFalse(usersWithHabits.isEmpty(), "Список пользователей с привычками не должен быть пустым.");

        for (UserDto userDto : usersWithHabits) {
            assertNotNull(userDto.getHabitList(), "Список привычек пользователя не должен быть null.");
        }
    }

}
