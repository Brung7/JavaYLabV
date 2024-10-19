package by.vladimir.utils;

import by.vladimir.controller.DateOfCompletionController;
import by.vladimir.controller.HabitController;
import by.vladimir.controller.UserController;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.exception.ReturnToMainException;

import java.util.Scanner;

public class Command {
    public void showCommand(User user) throws ReturnToMainException {
        HabitController habitController = HabitController.getInstance();
        UserController userController = UserController.getInstance();
        DateOfCompletionController dateController = DateOfCompletionController.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        while (!flag) {
            Role role = user.getRole();

            switch (role) {
                case ADMIN:
                    System.out.println("""
                            Доступные команды:
                            0. Exit
                            1. Add habit
                            2. Update habit
                            3. Delete habit
                            4. Show list of habits
                            5. Show all habits all users
                            6. Show all users
                            7. Add date
                            8. Show date
                            9. Update date
                            10. Delete date
                            """);

                    int choiceAdmin = scanner.nextInt();
                    switch (choiceAdmin) {
                        case 0:
                            throw new ReturnToMainException();
                        case 1:
                            habitController.createHabitToUser(user);
                            break;
                        case 2:
                            habitController.updateHabit(user);
                            break;
                        case 3:
                            habitController.deleteHabit(user);
                            break;
                        case 4:
                            habitController.getAllUserHabits(user);
                            break;
                        case 5:
                            userController.showAllHabitsAllUsers();
                            break;
                        case 6:
                            userController.showALlUsers();
                            break;
                        case 7:
                            dateController.createDateToHabit(user);
                            break;
                        case 8:
                            dateController.showAllDateOfHabit(user);
                            break;
                        case 9:
                            dateController.updateDate(user);
                            break;
                        case 10:
                            dateController.deleteDate(user);
                            break;
                        default:
                            System.out.println("Неверная команда");

                    }

                    break;
                case USER:
                    System.out.println("""
                            Доступные команды:
                            0. Exit
                            1. Add habit
                            2. Update habit
                            3. Delete habit
                            4. Show list of habits
                            5. Add date to habit
                            6. Show all date
                            7. Update date
                            8. Delete date
                            """);

                    int choiceUser = scanner.nextInt();
                    switch (choiceUser) {
                        case 0:
                            throw new ReturnToMainException();
                        case 1:
                            habitController.createHabitToUser(user);
                            break;
                        case 2:
                            habitController.updateHabit(user);
                            break;
                        case 3:
                            habitController.deleteHabit(user);
                            break;
                        case 4:
                            habitController.getAllUserHabits(user);
                            break;
                        case 5:
                            dateController.createDateToHabit(user);
                            break;
                        case 6:
                            dateController.showAllDateOfHabit(user);
                            break;
                        case 7:
                            dateController.updateDate(user);
                            break;
                        case 8:
                            dateController.deleteDate(user);


                    }
                    break;
                default:
                    System.out.println("Неизвестная роль.");
                    break;
            }


        }
    }
}


