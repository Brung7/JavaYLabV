package by.vladimir.controller;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.service.HabitService;
import by.vladimir.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HabitController {

    private HabitService habitService;

    @Autowired
    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @ApiOperation(value = "Create habit", notes = "Create new habit")
    @PostMapping("/habit")
    public void addHabit(@RequestBody CreateHabitDto createHabitDto) {
        habitService.createHabit(createHabitDto);
    }

    @ApiOperation(value = "Update habit", notes = "Update habit")
    @PutMapping("/habit")
    public void updateHabit(@RequestBody HabitDto habitDto) {
        habitService.update(habitDto);
    }

    @ApiOperation(value = "Get all habits", notes = "Get all user's habits")
    @GetMapping("/habit")
    public ResponseEntity<List<Habit>> getAllUserHabit(@RequestBody User user) {
        List<Habit> habitList = habitService.getUserHabits(user);
        return ResponseEntity.ok(habitList);
    }

    @ApiOperation(value = "Delete habit", notes = "Delete habit")
    @DeleteMapping("/habit")
    public ResponseEntity<String> deleteHabit(@RequestBody Long id) {
        if (habitService.containById(id)) {
            habitService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Привычка удалена");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Привычка не найдена");
        }
    }

}