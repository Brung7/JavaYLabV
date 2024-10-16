package by.vladimir.controller;

import by.vladimir.dao.UserDao;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.service.HabitService;
import by.vladimir.service.UserService;
import by.vladimir.utils.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {
        private UserController userController;
        HabitService habitService = HabitService.getInstance();
         UserService userService = UserService.getINSTANCE();
        UserDao userDao = UserDao.getInstance();
        private Command command;
        @BeforeEach
        public void setUp () {
            userController = UserController.getInstance();
            command = Mockito.mock(Command.class);
        }
        @Test
        public void testRegistration () {
            userDao.getUsers().clear();
            String input = "test@example.com\npassword\nUSER\n";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            userController.registration();

            assertTrue(outContent.toString().contains("Registration is success"));
        }
//        @Test
//        public void testAuthentication() {
//            final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//            System.setOut(new PrintStream(outContent));
//
//            // Prepare test input
//            String input = "test@example.com\npassword123";
//            System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//            // Mock the userService.authentication() method
//            User testUser = new User("test@example.com", "password123", Role.USER);
//            Mockito.when(userService.authentication(Mockito.anyString())).thenReturn(Optional.of(testUser));
//
//            // Call the method to test
//            userController.authentication();
//
//            // Verify the output
//            assertTrue(outContent.toString().contains("Authentication success"));
//            assertTrue(outContent.toString().contains("Welcome test@example.com"));
//            Mockito.verify(command).showCommand(testUser);
//        }
        @Test
        public void testGetRole () {
            User user = new User("test@example.com", "password", Role.ADMIN);
            assertEquals("ADMIN", userController.getRole(user));
        }
//        @Test
//        public void testShowAllHabitsAllUsers () {
//            final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//            System.setOut(new PrintStream(outContent));
//
//            User user1 = new User("test1@example.com", "password1", Role.USER);
//            Habit habit1 = habitService.createHabit("Exercise", "Daily", Frequency.DAILY);
//            habitService.addHabitToUser(user1,habit1);
//
//            User user2 = new User("test2@example.com", "password2", Role.USER);
//            Habit habit2 = habitService.createHabit("Meditation", "Morning", Frequency.WEEKLY);
//            habitService.addHabitToUser(user2,habit2);
//
//            userController.showAllHabitsAllUsers();
//
//            String expectedOutput = "test1@example.com : Exercise Daily DAILY\n" +
//                    "test2@example.com : Meditation Morning WEEKLY\n";
//            assertEquals(expectedOutput, outContent.toString());
//        }
        @Test
        public void testShowAllUsers () {
            userDao.getUsers().clear();
            UserService userService = UserService.getINSTANCE();
            userService.registration("test1@example.com", "password", Role.USER);
            userService.registration("test2@example.com", "password", Role.ADMIN);
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            userController.showALlUsers();
            assertTrue(outContent.toString().contains("test1@example.com " + "password " + "USER"));
            assertTrue(outContent.toString().contains("test2@example.com "+ "password "+"ADMIN"));
        }
}
