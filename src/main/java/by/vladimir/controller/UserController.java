package by.vladimir.controller;

import by.vladimir.dto.CreateUserDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.User;
import by.vladimir.filter.RoleFilter;
import by.vladimir.service.implementation.UserServiceImpl;
import by.vladimir.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    private final JwtUtils jwtUtils;

    private final RoleFilter roleFilter;

    @Operation(summary = "Регистрация пользователя", description = "Регистрация нового пользователя")
    @ApiResponse(responseCode = "200", description = "Успешный запрос")
    @ApiResponse(responseCode = "404", description = "Неудачный запрос")
    @PostMapping("/register")
    public ResponseEntity<User> registration(@RequestBody CreateUserDto createUserDto){
        User user = userServiceImpl.registration(createUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Operation(summary = "Авторизация пользователя", description = "Авторизация пользователя")
    @ApiResponse(responseCode = "200", description = "Успешная авторизация")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @ApiResponse(responseCode = "401", description = "Неверный пароль")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CreateUserDto userDto){
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        Optional<User> optionalUser = userServiceImpl.authentication(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.getPassword().equals(password)){
                String token = jwtUtils.generateToken(email);
                return ResponseEntity.ok(token);
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный пароль");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не зарегестрирован");
        }
    }

    @Operation(summary = "Получение списка всех пользователей", description = "Получения списках всех зарегестрированных пользователей")
    @ApiResponse(responseCode = "200", description = "Успешный запрос")
    @ApiResponse(responseCode = "404", description = "Неудачный запрос")
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestBody CreateUserDto userDto){
        if(roleFilter.isAdmin(userDto.getRole())){
            List<UserDto> userDtos = userServiceImpl.getAllUsersWithHabits();
            return ResponseEntity.status(HttpStatus.OK).body(userDtos);
        }
        else {
            throw  new RuntimeException("Нет прав доступа");
        }
    }

    @Operation(summary = "Удаление пользователя", description = "Удаление пользователя администратором по id")
    @ApiResponse(responseCode = "200", description = "Пользователь удален")
    @ApiResponse(responseCode = "405", description = "Метод не доступен")
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody User user){
        if(roleFilter.isAdmin(user.getRole().name())){
            userServiceImpl.delete(user.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Пользователь удален");
        }
        else{
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Метод не доступен");
        }
    }
}