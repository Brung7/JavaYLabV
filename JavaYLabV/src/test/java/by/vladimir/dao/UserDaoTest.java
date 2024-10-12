package by.vladimir.dao;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao = UserDao.getInstance();
    }
    @Test
    public void testGetListOfUsers() {
        userDao.getUsers().clear();
        userDao.save("test1@example.com", "password", Role.USER);
        userDao.save("test2@example.com", "password", Role.ADMIN);
        assertEquals(2, userDao.getListOfUsers().size());
    }
    @Test
    void save(){
        UserDao userDao = UserDao.getInstance();
        boolean result = userDao.save("vladimir@mail.ru","qwert",Role.USER);
        Assertions.assertTrue(result);
    }
    @Test
    public void testFindByEmail() {
        userDao.save("test@example.com", "password", Role.USER);
        User foundUser = userDao.findByEmail("test@example.com");
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    public void testUpdate() {
        userDao.save("test@example.com", "password", Role.USER);
        User updatedUser = new User("test@example.com", "newpassword", Role.ADMIN);
        userDao.update(updatedUser);
        assertEquals(Role.ADMIN, userDao.findByEmail("test@example.com").getRole());
    }

    @Test
    public void testDelete() {
        userDao.save("test@example.com", "password", Role.USER);
        userDao.delete("test@example.com");
        assertNull(userDao.findByEmail("test@example.com"));
    }

    @Test
    public void testGetUsers() {
        userDao.save("test1@example.com", "password", Role.USER);
        userDao.save("test2@example.com", "password", Role.ADMIN);
        assertEquals(2, userDao.getUsers().size());
    }
}
