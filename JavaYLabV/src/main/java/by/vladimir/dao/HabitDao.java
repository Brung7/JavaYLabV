package by.vladimir.dao;
import by.vladimir.entity.Habit;
import java.util.*;

public class HabitDao {
    private static final HabitDao INSTANCE = new HabitDao();
    private HabitDao(){
    }
    public static HabitDao getInstance(){
        return INSTANCE;
    }
    //Map for storage all habits(Key userEmail)
    private final List<Habit> habits = new ArrayList<>();
    public void save(Habit habit){
        habits.add(habit);
    }
    public void delete(int index){
        habits.remove(index);
    }
    public Habit findByIndex(int index){
        return habits.get(index);
    }
    public void update(int index, Habit habit){
        habits.set(index,habit);
    }
    public List<Habit> getHabits(){
        return habits;
    }
}
