package by.vladimir.controller;
import by.vladimir.entity.Habit;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.exception.ReturnToMainException;
import by.vladimir.service.UserService;
import by.vladimir.utils.Command;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
public class UserController {
    private static final UserController INSTANCE = new UserController();
    private final UserService userService = UserService.getINSTANCE();
    Command command = new Command();
    public void registration(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.println("Введите роль");
        String role = scanner.nextLine();
        if(userService.registration(email,password,Role.valueOf(role))){
            System.out.println("Registration is success");
        }
        else {
            System.out.println("User is already registered");
        }
    }
    public void authentication() throws ReturnToMainException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя пользователя: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        Optional<User> userOptional = userService.authentication(email);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getPassword().equals(password)) {
                System.out.println("Authentication success");
                System.out.println("Welcome " + user.getEmail());
                command.showCommand(user);
            }
            else {
                System.out.println("Authentication failed");
                authentication();
            }
        }
        else{
            System.out.println("User not found");
            authentication();
        }
    }
    public String getRole(User user){
        return userService.getRole(user);
    }
    public void showAllHabitsAllUsers(){
        List<User> userList =userService.userList();
        for(User user:userList){
            for(Habit habit: user.getListOfHabits()){
                System.out.println(user.getEmail() + " : " + habit.getName() + " " + habit.getDescription() + " " + habit.getFrequency());
            }
        }
    }
    public void showALlUsers(){
        List<User> users = userService.userList();
        for (User user:users){
            System.out.println(user.getEmail() +" " + user.getPassword()+ " " + user.getRole());
        }
    }
    private UserController(){
    }
    public static UserController getInstance(){
        return INSTANCE;
    }
}

