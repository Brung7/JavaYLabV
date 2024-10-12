package by.vladimir.utils;

import by.vladimir.controller.HabitController;
import by.vladimir.controller.UserController;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.exception.ReturnToMainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;

public class CommandTest {
    private HabitController habitController;
    private UserController userController;
    private Command command;

    @BeforeEach
    public void setUp() {
        habitController = Mockito.mock(HabitController.class);
        userController = Mockito.mock(UserController.class);
        command = new Command();
    }
    @Test
    public void testShowCommand_Admin() throws ReturnToMainException {
        User adminUser = new User("admin@example.com", "adminpassword", Role.ADMIN);
        String input = "1";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        command.showCommand(adminUser);
        Mockito.verify(habitController).addHabitToUser(adminUser);
    }
    @Test
    public void testShowCommand_User() throws ReturnToMainException {
        User userUser = new User("user@example.com", "userpassword", Role.USER);
        String input = "1";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        command.showCommand(userUser);
        Mockito.verify(habitController).addHabitToUser(userUser);
    }
}
