package by.vladimir.controller;

import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.entity.User;
import by.vladimir.service.DateOfCompletionService;
import by.vladimir.service.HabitService;

import java.util.List;
import java.util.Scanner;

public class DateOfCompletionController {
    private static final DateOfCompletionController INSTANCE = new DateOfCompletionController();

    private DateOfCompletionController() {
    }

    public static DateOfCompletionController getInstance() {
        return INSTANCE;
    }

    private final HabitController habitController = HabitController.getInstance();
    private final DateOfCompletionService dateOfCompletionService = DateOfCompletionService.getInstance();
    private final HabitService habitService = HabitService.getInstance();

    /**
     *
     * @param user
     * Принимает user для нахождения его привычек и добавления в привычку даты
     */
    public void createDateToHabit(User user) {
        habitController.getAllUserHabits(user);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер привычки");
        Long id = scanner.nextLong();
        if (habitService.containById(id)) {
            List<DateOfCompletion> dateList = dateOfCompletionService.findByHabitId(id);
            System.out.println(dateList);
            System.out.println("Введите дату в формате YYYY-MM-dd");
            String date = scanner.next();
            CreateDateOfComplDto dateDto = CreateDateOfComplDto.builder()
                    .date(date)
                    .habitId(id)
                    .build();
            dateOfCompletionService.createDateOfCompletion(dateDto);
        } else {
            System.out.println("Привычки с таким id нет");
        }
    }

    /**
     *
     * @param user
     * Принимает user для получения привычек и обновления даты у выбранной привычки
     */
    public void updateDate(User user) {
        habitController.getAllUserHabits(user);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер привычки");
        Long id = scanner.nextLong();
        if (habitService.containById(id)) {
            List<DateOfCompletion> dateList = dateOfCompletionService.findByHabitId(id);
            System.out.println(dateList);
            System.out.println("Введите номер даты");
            Long idDate = scanner.nextLong();
            if (dateOfCompletionService.containById(idDate)) {
                System.out.println("Введите новую дату");
                String date = scanner.next();
                DateOfCompletionDto dateUpdateDto = DateOfCompletionDto
                        .builder()
                        .id(idDate)
                        .date(date)
                        .build();
                dateOfCompletionService.update(dateUpdateDto);
            } else {
                System.out.println("Привычки с таким id нет");
            }
        } else {
            System.out.println("Даты с таким id не существует");
        }

    }

    /**
     *
     * @param user
     * Принимает user для удаления даты выполнения конкретной привычки
     */
    public void deleteDate(User user) {
        habitController.getAllUserHabits(user);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер привычки");
        Long id = scanner.nextLong();
        if (habitService.containById(id)) {
            List<DateOfCompletion> dateList = dateOfCompletionService.findByHabitId(id);
            System.out.println(dateList);
            System.out.println("Введите номер даты");
            Long idDate = scanner.nextLong();
            if (dateOfCompletionService.containById(idDate)) {
                dateOfCompletionService.delete(idDate);
            } else {
                System.out.println("Даты с таким id не существует");
            }
        } else {
            System.out.println("Привычки с таким id нет");
        }
    }

    /**
     *
     * @param user
     * Выводит на консоль все даты конкретной привычки
     */
    public void showAllDateOfHabit(User user) {
        habitController.getAllUserHabits(user);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер привычки");
        Long id = scanner.nextLong();
        List<DateOfCompletion> dateList = dateOfCompletionService.findByHabitId(id);
        System.out.println(dateList);
    }


}
