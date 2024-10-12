package by.vladimir.dao;

import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HabitDaoTest {
    private HabitDao habitDao;

    @BeforeEach
    public void setUp() {
        habitDao = HabitDao.getInstance();
    }

    @Test
    public void testSave() {
        habitDao.getHabits().clear();
        Habit habit = new Habit("Exercise", "qwererte", Frequency.DAILY);
        habitDao.save(habit);
        assertEquals(1, habitDao.getHabits().size());
        assertEquals(habit, habitDao.getHabits().get(0));
    }

    @Test
    public void testDelete() {
        habitDao.getHabits().clear();
        Habit habit = new Habit("Exercise", "qwererte", Frequency.MONTHLY);
        habitDao.save(habit);
        habitDao.delete(0);
        assertEquals(0, habitDao.getHabits().size());
    }

    @Test
    public void testFindByIndex() {
        habitDao.getHabits().clear();
        Habit habit = new Habit("Exercise", "qwererte", Frequency.DAILY);
        habitDao.save(habit);
        assertEquals(habit, habitDao.findByIndex(0));
    }

    @Test
    public void testUpdate() {
        habitDao.getHabits().clear();
        Habit habit1 = new Habit("Exercise", "qwererte", Frequency.DAILY);
        Habit habit2 = new Habit("Meditation", "qwewdfsfsfds",Frequency.WEEKLY);
        habitDao.save(habit1);
        habitDao.update(0, habit2);
        assertEquals(habit2, habitDao.getHabits().get(0));
    }
}
