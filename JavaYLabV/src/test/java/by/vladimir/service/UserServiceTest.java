package by.vladimir.service;

import by.vladimir.dao.UserDao;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;
    UserDao userDao = UserDao.getInstance();

    @BeforeEach
    public void setUp() {
        userService = UserService.getINSTANCE();
    }
    @Test
    public void testUserList() {
        userDao.getUsers().clear();
        userService.registration("test1@example.com", "password", Role.USER);
        userService.registration("test2@example.com", "password", Role.ADMIN);
        assertEquals(2, userService.userList().size());
    }
    @Test
    public void testRegistration() {
        userDao.getUsers().clear();
        assertTrue(userService.registration("test@example.com", "password", Role.USER));
        assertFalse(userService.registration("test@example.com", "password", Role.USER));
    }

    @Test
    public void testAuthentication() {
        userService.registration("test@example.com", "password", Role.USER);
        Optional<User> userOptional = userService.authentication("test@example.com");
        assertTrue(userOptional.isPresent());
        assertEquals("test@example.com", userOptional.get().getEmail());
    }

    @Test
    public void testGetRole() {
        User user = new User("test@example.com", "password", Role.ADMIN);
        assertEquals("ADMIN", userService.getRole(user));
    }
}
