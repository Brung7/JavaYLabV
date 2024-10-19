package by.vladimir.controller;

import by.vladimir.dto.CreateUserDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.exception.ReturnToMainException;
import by.vladimir.service.UserService;
import by.vladimir.utils.Command;
//import by.vladimir.utils.Command;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserController {
    private static final UserController INSTANCE = new UserController();
    private final UserService userService = UserService.getINSTANCE();

    Command command = new Command();

    /**
     * Регистрация пользователя
     */
    public void registration() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.println("Введите роль");
        String role = scanner.nextLine();
        CreateUserDto createUserDto = CreateUserDto.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
        if (!userService.isUserExist(email)) {
            userService.registration(createUserDto);
            System.out.println("Registration is success");
        } else {
            System.out.println("User is already registered");
        }
    }

    /**
     *
     * @throws ReturnToMainException
     * Аутентификация пользователя
     */
    public void authentication() throws ReturnToMainException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя пользователя: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        Optional<User> userOptional = userService.authentication(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                System.out.println("Authentication success");
                System.out.println("Welcome " + user.getEmail());
                command.showCommand(user);
            } else {
                System.out.println("Authentication failed");
                authentication();
            }
        } else {
            System.out.println("User not found");
            authentication();
        }
    }

    /**
     * Выводи всех пользователей со всеми привычками на консоль
     */
    public void showAllHabitsAllUsers() {
        List<UserDto> usersWithHabits = userService.getAllUsersWithHabits();
        for (UserDto userDto : usersWithHabits) {
            System.out.println("User ID: " + userDto.getId());
            System.out.println("User Email: " + userDto.getEmail());
            System.out.println("Habits:");
            for (HabitDto habit : userDto.getHabitList()) {
                System.out.println("Habit ID: " + habit.getId());
                System.out.println("Habit Name: " + habit.getName());
                System.out.println("Habit Description: " + habit.getDescription());
                System.out.println("Habit Frequency: " + habit.getFrequency());
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * Выводит всех пользователей на консоль
     */
    public void showALlUsers() {
        List<User> users = userService.userList();
        for (User user : users) {
            System.out.println(user.getEmail() + " " + user.getPassword() + " " + user.getRole());
        }
    }

    private UserController() {
    }

    public static UserController getInstance() {
        return INSTANCE;
    }
}

