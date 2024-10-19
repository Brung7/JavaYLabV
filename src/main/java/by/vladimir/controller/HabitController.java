package by.vladimir.controller;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.service.HabitService;

import java.util.List;
import java.util.Scanner;

public class HabitController {
    private static final HabitController INSTANCE = new HabitController();
    private final HabitService habitService = HabitService.getInstance();

    /**
     *
     * @param user
     * Выводит на консоль все привычки конкретного пользователя
     */
    public void getAllUserHabits(User user) {
        List<Habit> habitList = habitService.getUserHabits(user);
        if (habitList.isEmpty()) {
            System.out.println("List of habits is empty");
        } else {
            for (Habit habit : habitList) {
                System.out.println(habit);
            }
        }
    }

    /**
     *
     * @param user
     * Создает привычки и закрепляет за конкретным пользователем
     */
    public void createHabitToUser(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название");
        String name = scanner.nextLine();
        System.out.println("Введите описание");
        String description = scanner.nextLine();
        System.out.println("Введите частоту выполнения");
        String frequency = scanner.nextLine();
        CreateHabitDto createHabitDto = CreateHabitDto.builder()
                .name(name)
                .description(description)
                .frequency(frequency)
                .userId(user.getId())
                .build();
        habitService.createHabit(createHabitDto);
    }

    /**
     *
     * @param user
     * Позволяет пользователю обновить привычку
     */
    public void updateHabit(User user) {
        getAllUserHabits(user);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер привычки");
        Long id = scanner.nextLong();
        if (habitService.containById(id)) {
            System.out.println("Введите новое название");
            String name = scanner.next();
            System.out.println("Введите новое описание");
            String description = scanner.next();
            System.out.println("Введите новою частоту");
            String frequency = scanner.next();
            HabitDto habitDto = HabitDto.builder()
                    .id(id)
                    .name(name)
                    .description(description)
                    .frequency(Frequency.valueOf(frequency))
                    .build();
            habitService.update(habitDto);
        } else {
            System.out.println("Привычки с таким номером нет");
        }
    }

    /**
     *
     * @param user
     * Позволяет пользователю удлалить привычку
     */
    public void deleteHabit(User user) {
        getAllUserHabits(user);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер привычки, которую хотитет удалить");
        Long id = scanner.nextLong();
        if (habitService.containById(id)) {
            habitService.delete(id);
        } else {
            System.out.println("Привычки с таким номером нет");
        }
    }

    private HabitController() {
    }

    public static HabitController getInstance() {
        return INSTANCE;
    }
}
