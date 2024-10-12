package by.vladimir;

import by.vladimir.controller.UserController;
import by.vladimir.exception.ReturnToMainException;

import java.util.Scanner;

public class HabitTrackingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserController userController = UserController.getInstance();
        while (true){
            {
                try {
                    System.out.println("""
                            1. Регистрация пользователя
                            2. Авторизация
                            3. Выход""");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            userController.registration();
                            break;
                        case 2:
                            userController.authentication();
                        case 3:
                            System.exit(0);
                    }
                } catch (ReturnToMainException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
