package by.vladimir.service;

import by.vladimir.dao.HabitDao;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HabitServiceTest {
    private HabitService habitService;
    HabitDao habitDao = HabitDao.getInstance();
    private User user;
    private Habit habit;

    @BeforeEach
    public void setUp() {
        habitService = HabitService.getInstance();
        user = new User("test@example.com", "password", Role.USER);
        habit = new Habit("Exercise",  new ArrayList<>(),"Daily workout");
    }

    @Test
    public void testGetUserHabitsNotEmpty() {
        Habit habit = new Habit("Exercise", "aaaaaaaaaaaaa", Frequency.WEEKLY);
        habitService.addHabitToUser(user,habit);
        List<Habit> userHabits = habitService.getUserHabits(user);
        assertFalse(userHabits.isEmpty());
        assertTrue(userHabits.contains(habit));
    }

    @Test
    public void testGetUserHabitsEmpty() {
        habitDao.getHabits().clear();
        List<Habit> userHabits = habitService.getUserHabits(user);
        assertTrue(userHabits.isEmpty());
    }

    @Test
    public void testAddHabitToUser() {
        Habit habit = new Habit("Exercise", "bbbbbbbbb",Frequency.MONTHLY);
        habitService.addHabitToUser(user, habit);
        assertTrue(user.getListOfHabits().contains(habit));
    }

    @Test
    public void testCreateHabit() {
        habitDao.getHabits().clear();
        String name = "Exercise";
        String description = "Daily exercise routine";
        Frequency frequency = Frequency.DAILY;
        Habit habit = habitService.createHabit(name, description, frequency);
        assertTrue(habitService.containByIndex(0));
    }
    @Test
    public void testUpdate() {
        habitDao.getHabits().clear();
        Habit habit = habitService.createHabit("Exercise", "Daily exercise routine", Frequency.DAILY);
        habitService.addHabitToUser(user,habit);
        String newName = "Running";
        String newDescription = "Morning running routine";
        Frequency newFrequency = Frequency.WEEKLY;
        habitService.update(0, user,newName, newDescription, newFrequency);
        assertEquals(newName, habit.getName());
        assertEquals(newDescription, habit.getDescription());
        assertEquals(newFrequency, habit.getFrequency());
    }

    @Test
    public void testDelete() {
        habitDao.getHabits().clear();
        Habit habit = habitService.createHabit("Exercise", "badaba",Frequency.DAILY);
        Habit habit2 = habitService.createHabit("Meditation", "qqqqq",Frequency.MONTHLY);
        habitService.addHabitToUser(user,habit);
        habitService.addHabitToUser(user,habit2);
        habitService.delete(0,user);
        List<Habit> habitList = user.getListOfHabits();
        assertFalse(habitList.contains(habit));
        assertTrue(habitList.contains(habit2));
    }
    @Test
    public void testGetAllDates_NotNull() {
        List<Date> dates = new ArrayList<>();
        dates.add(new Date());
        habit.setCompletion(dates);
        List<Date> result = habitService.getAllDates(habit);
        assertNotNull(result);
        assertEquals(dates, result);
    }

    @Test
    public void testGetAllDates_Null() {
        habit.setCompletion(null);
        List<Date> result = habitService.getAllDates(habit);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testUpdateDate() {
        List<Date> dates = new ArrayList<>();
        dates.add(new Date());
        dates.add(new Date());
        habit.setCompletion(dates);
        Date newDate = new Date();
        habitService.updateDate(0, habit, newDate);
        List<Date> updatedDates = habit.getCompletion();
        assertEquals(newDate, updatedDates.get(0));
    }
    @Test
    public void testAddDateOfCompletion_ExistingList() {
        List<Date> dates = new ArrayList<>();
        Date date1 = new Date();
        dates.add(date1);
        habit.setCompletion(dates);
        Date newDate = new Date();
        int index = 1;
        habitService.addDateOfCompletion(index, newDate, habit);
        List<Date> updatedDates = habit.getCompletion();
        assertEquals(newDate, updatedDates.get(index));
    }

    @Test
    public void testAddDateOfCompletion_NewList() {
        habit.setCompletion(null);
        Date newDate = new Date();
        int index = 0;
        habitService.addDateOfCompletion(index, newDate, habit);
        List<Date> updatedDates = habit.getCompletion();
        assertNotNull(updatedDates);
        assertEquals(1, updatedDates.size());
        assertEquals(newDate, updatedDates.get(index));
    }
}
