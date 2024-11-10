package by.vladimir.service;

import by.vladimir.dao.UserDao;
import by.vladimir.dto.CreateUserDto;
import by.vladimir.entity.User;
import by.vladimir.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserDao userDao;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("database.url", postgreSQLContainer::getJdbcUrl);
        registry.add("database.username", postgreSQLContainer::getUsername);
        registry.add("database.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    public void setUp() {
        postgreSQLContainer.start();
    }

    @AfterEach
    public void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    public void testRegistration() {
        CreateUserDto createUserDto = new CreateUserDto("test@example.com", "password", "USER");

        User registeredUser = userService.registration(createUserDto);
        Optional<User> foundUser = userDao.findByEmail("test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals(registeredUser.getEmail(), foundUser.get().getEmail());
    }

    @Test
    public void testAuthentication() {
        CreateUserDto createUserDto = new CreateUserDto("test@example.com", "password", "USER");
        User registeredUser = userService.registration(createUserDto);

        Optional<User> authenticatedUser = userService.authentication("test@example.com");

        assertTrue(authenticatedUser.isPresent());
        assertEquals(registeredUser.getEmail(), authenticatedUser.get().getEmail());
    }

    @Test
    public void testFindById() {
        CreateUserDto createUserDto = new CreateUserDto("test@example.com", "password", "USER");
        User registeredUser = userService.registration(createUserDto);

        User foundUser = userService.findById(registeredUser.getId());

        assertNotNull(foundUser);
        assertEquals(registeredUser.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testIsUserExist() {
        CreateUserDto createUserDto = new CreateUserDto("test@example.com", "password", "USER");

        User registeredUser = userService.registration(createUserDto);

        boolean userExists = userService.isUserExist("test@example.com");

        assertTrue(userExists);
    }
}
