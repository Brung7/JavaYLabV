package by.vladimir.dao;

import by.vladimir.entity.Role;
import by.vladimir.entity.User;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.*;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;


@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));


    @BeforeEach
    public  void setUp(){
        postgreSQLContainer.start();
    }

    @AfterEach
    public  void tearDown() {

        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) throws SQLException {
        registry.add("database.url", postgreSQLContainer::getJdbcUrl);
        registry.add("database.username", postgreSQLContainer::getUsername);
        registry.add("database.password", postgreSQLContainer::getPassword);

    }


    @Test
    public void testSaveUser() throws Exception {
            User user = new User();
            user.setEmail("test@example.com");
            user.setPassword("password");
            user.setRole(Role.USER);

            User savedUser = userDao.save(user);

            assertThat(savedUser).isNotNull();
            assertThat(savedUser.getId()).isNotNull();
            assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
            userDao.delete(savedUser.getId());
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        User savedUser = userDao.save(user);

        Optional<User> foundUser = userDao.findById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
        userDao.delete(savedUser.getId());
    }

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        User savedUser = userDao.save(user);

        Optional<User> foundUser = userDao.findByEmail("test@example.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
        userDao.delete(savedUser.getId());

    }

    @Test
    public void testUpdate(){

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        User savedUser = userDao.save(user);

        assertNotNull(savedUser.getId());

        User user2 = new User();
        user2.setId(savedUser.getId());
        user2.setEmail("test@example2.com");
        user2.setPassword("password2");
        user2.setRole(Role.ADMIN);
        userDao.update(user2);

        User updatedUser = userDao.findById(savedUser.getId()).orElseThrow();

        assertThat(updatedUser.getEmail()).isEqualTo("test@example2.com");
        assertThat(updatedUser.getEmail()).isNotEqualTo(savedUser.getEmail());

        userDao.delete(updatedUser.getId());;
    }

    @Test
    public void testDelete(){
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        User savedUser = userDao.save(user);
        userDao.delete(savedUser.getId());

       Optional<User> deleteUser = userDao.findById(savedUser.getId());

        assertThat(deleteUser).isEmpty();
    }


}