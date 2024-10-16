package by.vladimir.controller;

import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.service.HabitService;
import by.vladimir.utils.Command;
import by.vladimir.utils.DateFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static by.vladimir.entity.Frequency.DAILY;
import static by.vladimir.entity.Frequency.MONTHLY;
import static org.junit.jupiter.api.Assertions.*;

public class HabitControllerTest {
    private HabitController habitController;
    HabitService habitService = HabitService.getInstance();
    private User user;
    private Command command;
    private Habit habit;

    @BeforeEach
    public void setUp() {
        habitController = HabitController.getInstance();
        user = new User("test@example.com", "password", Role.USER);
        command = new Command();
        habit =new Habit("Name", new ArrayList<>(),"Description");
        habitService = Mockito.mock(HabitService.class);
    }

    @Test
    public void testGetAllUserHabitsEmptyList() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        habitController.getAllUserHabits(user);
        assertTrue(outContent.toString().contains("List of habits is empty"));
    }

    @Test
    public void testAddHabitToUser() {
        ByteArrayInputStream in = new ByteArrayInputStream("Exercise\nDaily exercise routine\nDAILY".getBytes());
        System.setIn(in);
        habitController.addHabitToUser(user);
        assertEquals(1, user.getListOfHabits().size());
    }

    @Test
    public void testUpdateHabit() {
        habitService.addHabitToUser(user,habitService.createHabit("Exercise", "Eeeeee", Frequency.MONTHLY));
        ByteArrayInputStream in = new ByteArrayInputStream("0\nRunning\nMorning\nWEEKLY".getBytes());
        System.setIn(in);
        habitController.updateHabit(user);
        assertEquals("Running", user.getListOfHabits().get(0).getName());
        assertEquals("Morning", user.getListOfHabits().get(0).getDescription());
        assertEquals(Frequency.WEEKLY, user.getListOfHabits().get(0).getFrequency());
    }

    @Test
    public void testDeleteHabit() {
        Habit habit = habitService.createHabit("Exercise", "Daily exercise routine",Frequency.DAILY);
        habitService.addHabitToUser(user,habit);
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        habitController.deleteHabit(user);
        assertTrue(user.getListOfHabits().isEmpty());
    }
    @Test
    public void testShowAllDate() {
        user.setListOfHabits(new ArrayList<>());
        habitService.addHabitToUser(user,habit);
        user.getListOfHabits().add(new Habit("Exercise", new ArrayList<>(),"Daily workout"));
        String input = "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        habitController.showAllDate(user);
    }

}
