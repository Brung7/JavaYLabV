package by.vladimir.controller;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.service.implementation.HabitServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HabitController {

    private final HabitServiceImpl habitServiceImpl;

    @Operation(summary = "Создание привычки", description = "Создание новой привычки")
    @ApiResponse(responseCode = "201", description = "Привычка создана")
    @PostMapping("/habit")
    public ResponseEntity<Object> addHabit(@RequestBody CreateHabitDto createHabitDto) {
        Habit habit = habitServiceImpl.create(createHabitDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(habit);
    }

    @Operation(summary = "Обновление привычки", description = "Обновление привычки")
    @ApiResponse(responseCode = "201", description = "Привычка обновлена")
    @ApiResponse(responseCode = "404", description = "Привычка не найдена")
    @PutMapping("/habit")
    public ResponseEntity<Object> updateHabit(@RequestBody HabitDto habitDto) {
        if(habitServiceImpl.containById(habitDto.getId())){
            Habit habit = habitServiceImpl.update(habitDto);
            return ResponseEntity.status(HttpStatus.OK).body(habit);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Не удалось найти привычку");
        }

    }

    @Operation(summary = "Получение списка привычек", description = "Получение списка всех привычек пользователя")
    @ApiResponse(responseCode = "200", description = "Список привычек получен успешно")
    @GetMapping("/habit")
    public ResponseEntity<List<Habit>> getAllUserHabit(@RequestBody User user) {
        List<Habit> habitList = habitServiceImpl.getAll(user);
        return ResponseEntity.status(HttpStatus.OK).body(habitList);
    }

    @Operation(summary = "Удаление привычки", description = "Удаление привычки")
    @ApiResponse(responseCode = "200", description = "Привычка обновлена")
    @ApiResponse(responseCode = "404", description = "Привычка не найдена")
    @DeleteMapping("/habit")
    public ResponseEntity<String> deleteHabit(@RequestBody Long id) {
        if (habitServiceImpl.containById(id)) {
            habitServiceImpl.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Привычка удалена");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Привычка не найдена");
        }
    }

}