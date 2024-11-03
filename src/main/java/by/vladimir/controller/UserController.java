package by.vladimir.controller;

import by.vladimir.dto.CreateUserDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.User;
import by.vladimir.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Register user", notes = "Register new user")
    @PostMapping("/register")
    public User registration(@RequestBody CreateUserDto createUserDto){
        return userService.registration(createUserDto);
    }

    @ApiOperation(value = "Login user", notes = "Login user")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CreateUserDto userDto){
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        Optional<User> optionalUser = userService.authentication(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.getPassword().equals(password)){
                String token = Jwts.builder()
                        .setSubject(userDto.getEmail())
                        .signWith(SignatureAlgorithm.HS256, "SecretKey")
                        .compact();
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

    @ApiOperation(value = "Get users",notes = "Get list of all users")
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestBody CreateUserDto userDto){
        if(userDto.getRole().equals("ADMIN")){
            List<UserDto> userDtos = userService.getAllUsersWithHabits();
            return ResponseEntity.ok(userDtos);
        }
        else {
            throw  new RuntimeException("Нет прав доступа");
        }
    }
}