package by.vladimir.controller;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.service.HabitService;
import by.vladimir.utils.DateFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
public class HabitController {
    private static final HabitController INSTANCE = new HabitController();
    private final HabitService habitService = HabitService.getInstance();
    public void getAllUserHabits(User user){
        List<Habit> habitList = habitService.getUserHabits(user);
        if(habitList.isEmpty()){
            System.out.println("List of habits is empty");
        }
        else {
            for(Habit habit: habitList){
                System.out.println(habit);
            }
        }
    }
    public void addHabitToUser(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название");
        String name = scanner.nextLine();
        System.out.println("Введите описание");
        String description = scanner.nextLine();
        System.out.println("Введите частоту выполнения");
        String frequency = scanner.nextLine();
        Habit habit = habitService.createHabit(name, description,Frequency.valueOf(frequency));
        habitService.addHabitToUser(user,habit);
    }
    public void updateHabit(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер привычки");
        int index = scanner.nextInt();
        if(habitService.containByIndex(index)){
            System.out.println("Введите новое название");
            String name = scanner.next();
            System.out.println("Введите новое описание");
            String description = scanner.next();
            System.out.println("Введите новою частоту");
            String frequency = scanner.next();
            habitService.update(index,user,name,description,Frequency.valueOf(frequency));
        }
        else{
            System.out.println("Привычки с таким номером нет");
        }
    }
    public void deleteHabit(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер привычки, которую хотитет удалить");
        int index = scanner.nextInt();
        habitService.delete(index,user);
    }
    public void addDateOfCompletion(User user){
        Scanner scanner = new Scanner(System.in);
        getAllUserHabits(user);
        System.out.println("Введите номер привычки");
        int index = scanner.nextInt();
        Habit habit = user.getListOfHabits().get(index);
        System.out.println("Введите дату");
        String dateStr = scanner.next();
        DateFormatter dateFormatter = new DateFormatter();
        Date date = dateFormatter.formatterStrToDate(dateStr);
        habitService.addDateOfCompletion(index,date,habit);
    }
    public void updateDate(User user){
        Scanner scanner = new Scanner(System.in);
        getAllUserHabits(user);
        System.out.println("Введите номер привычки");
        int index = scanner.nextInt();
        Habit habit = user.getListOfHabits().get(index);
        System.out.println("Введите дату");
        String dateStr = scanner.next();
        DateFormatter dateFormatter = new DateFormatter();
        Date date = dateFormatter.formatterStrToDate(dateStr);
        habitService.updateDate(index,habit,date);
    }
    public void deleteDate(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер привычки");
        int numb = scanner.nextInt();
        Habit habit = habitService.findHabitByIndex(numb,user);
        habitService.getAllDates(habit);
        showAllDate(user);
        System.out.println("Выберете дату для удаления");
        int index = scanner.nextInt();
        List<Date> dateList = habitService.getAllDates(habit);
        dateList.remove(index);
    }
    public void showAllDate(User user){
        getAllUserHabits(user);
        System.out.println("Введите номер привычки");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        Habit habit = user.getListOfHabits().get(index);
        List<Date> dateList = habitService.getAllDates(habit);
        if(dateList.isEmpty()){
            System.out.println("Лист дат пуст");
        }
        else {
            for(Date date:dateList){
                System.out.println(date);
            }
        }
    }
    private HabitController(){
    }
    public static HabitController getInstance(){
        return INSTANCE;
    }
}
