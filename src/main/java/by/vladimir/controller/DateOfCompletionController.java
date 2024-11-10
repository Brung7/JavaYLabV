package by.vladimir.controller;

import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.service.implementation.DateOfCompletionServiceImpl;
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
public class DateOfCompletionController {

    private final DateOfCompletionServiceImpl dateOfCompletionServiceImpl;

    @Operation(summary = "Создание даты", description = "Создание новой даты выполнения привычки")
    @ApiResponse(responseCode = "201", description = "Дата создана успешно")
    @PostMapping("/date")
    public ResponseEntity<String> createDateOfCompletion(@RequestBody CreateDateOfComplDto createDateOfComplDto){
        dateOfCompletionServiceImpl.create(createDateOfComplDto);
        return ResponseEntity.status(HttpStatus.OK).body("Дата добавлена");
    }

    @Operation(summary = "Обновление даты", description = "Обновление даты выполнения привычки")
    @ApiResponse(responseCode = "200", description = "Дата обновлена успешно")
    @PutMapping("/date")
    public ResponseEntity<String> updateDateOfCompletion(@RequestBody DateOfCompletionDto dateOfCompletionDto) {
        dateOfCompletionServiceImpl.update(dateOfCompletionDto);
        return ResponseEntity.status(HttpStatus.OK).body("Дата обновлена");
    }

    @Operation(summary = "Удаление даты", description = "Удаление даты выполнения привычки")
    @ApiResponse(responseCode = "200", description = "Дата удалена успешно")
    @ApiResponse(responseCode = "404", description = "Дата не найдена")
    @DeleteMapping("/date")
    public ResponseEntity<String> deleteDateOfCompletion(@RequestBody Long id){
        if(dateOfCompletionServiceImpl.containById(id)){
            dateOfCompletionServiceImpl.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Дата удалена");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Дата не найдена");
        }
    }

    @Operation(summary = "Получение списка дат", description = "Получение списка всех дат выполнения привычки")
    @ApiResponse(responseCode = "200", description = "Список дат успешно")
    @GetMapping("/date")
    public ResponseEntity<List<DateOfCompletion>> getAllHabitDate(@RequestBody Long id){
        return ResponseEntity.status(HttpStatus.OK).body(dateOfCompletionServiceImpl.findByHabitId(id));
    }
}