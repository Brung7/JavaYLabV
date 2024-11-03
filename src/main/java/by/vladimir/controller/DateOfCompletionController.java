package by.vladimir.controller;

import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.service.DateOfCompletionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DateOfCompletionController {

    private DateOfCompletionService dateOfCompletionService;

    @Autowired
    public DateOfCompletionController(DateOfCompletionService dateOfCompletionService) {
        this.dateOfCompletionService = dateOfCompletionService;
    }

    @ApiOperation(value = "Create Date", notes = "Create date of completion")
    @PostMapping("/date")
    public void createDateOfCompletion(@RequestBody CreateDateOfComplDto createDateOfComplDto){
        dateOfCompletionService.createDateOfCompletion(createDateOfComplDto);
    }

    @ApiOperation(value = "Update Date", notes = "Update date of completion")
    @PutMapping("/date")
    public void updateDateOfCompletion(@RequestBody DateOfCompletionDto dateOfCompletionDto){
        dateOfCompletionService.update(dateOfCompletionDto);
    }

    @ApiOperation(value = "Delete Date", notes = "Delete date of completion")
    @DeleteMapping("/date")
    public ResponseEntity<String> deleteDateOfCompletion(@RequestBody Long id){
        if(dateOfCompletionService.containById(id)){
            dateOfCompletionService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Дата удалена");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Дата не найдена");
        }
    }

    @ApiOperation(value = "Get all Date", notes = "Get all date of completion of habit")
    @GetMapping("/date")
    public void getAllHabitDate(@RequestBody Long id){
        dateOfCompletionService.findByHabitId(id);
    }
}