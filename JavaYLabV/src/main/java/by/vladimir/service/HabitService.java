package by.vladimir.service;
import by.vladimir.dao.HabitDao;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;

import java.util.*;

public class HabitService {
    private static final HabitService INSTANCE = new HabitService();
    private final HabitDao habitDao = HabitDao.getInstance();
    public List<Habit> getUserHabits(User user){
        Optional<List<Habit>> optionalHabits = Optional.ofNullable(user.getListOfHabits());
        if(optionalHabits.isPresent()) {
            if (!user.getListOfHabits().isEmpty()) {
                return user.getListOfHabits();
            } else {
                return Collections.emptyList();
            }
        }
        else{
            return new ArrayList<>();
        }
    }
    public void addHabitToUser(User user, Habit habit){
        List<Habit> habitList = user.getListOfHabits();
        if(habitList == null){
            habitList = new ArrayList<>();
            habitList.add(habit);
            user.setListOfHabits(habitList);
        }
        else {
            habitList.add(habit);
        }
    }
//    public List<Habit> getAllHabits(User user){
//        return user.getListOfHabits();
//    }
    public Habit createHabit(String name,String description, Frequency frequency){
        Habit habit = new Habit(name, description, frequency);
        habitDao.save(habit);
        return habit;
    }
    public void update(int index, User user,String name, String description, Frequency frequency){
            Habit habit = user.getListOfHabits().get(index);
            habit.setName(name);
            habit.setDescription(description);
            habit.setFrequency(frequency);
            habitDao.update(index,habit);
            getUserHabits(user).set(index,habit);
            //addHabitToUser(user,habit);
    }
    public boolean containByIndex(int index){
        Optional<Habit> habitOptional = Optional.ofNullable(habitDao.findByIndex(index));
        if(habitOptional.isPresent()){
            return true;
        }
        else {
            return false;
        }
    }
    public Habit findHabitByIndex(int index, User user){
        return user.getListOfHabits().get(index);
    }
    public void delete(int index, User user){
       List<Habit> habitList = user.getListOfHabits();
       habitList.remove(index);
       habitDao.delete(index);
    }
    public void addDateOfCompletion(int index, Date date, Habit habit){
        Optional<List<Date>> dateOptional = Optional.ofNullable(habit.getCompletion());
        if(dateOptional.isPresent()){
            habit.getCompletion().add(index,date);
        }
        else{
            List<Date> dates = new ArrayList<>();
            dates.add(index,date);
            habit.setCompletion(dates);
        }
    }
    public List<Date> getAllDates(Habit habit){
        Optional<List<Date>> dateOptional = Optional.ofNullable(habit.getCompletion());
        if(dateOptional.isPresent()){
            return habit.getCompletion();
        }
        else{
            return new ArrayList<>();
        }
    }
    public void updateDate(int index, Habit habit,Date date){
        List<Date> dateList = habit.getCompletion();
        dateList.set(index,date);
    }
    private HabitService(){}
    public static HabitService getInstance(){
        return INSTANCE;
    }

}
