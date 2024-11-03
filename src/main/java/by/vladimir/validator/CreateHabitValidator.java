package by.vladimir.validator;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Frequency;
import org.springframework.stereotype.Component;

@Component
public class CreateHabitValidator implements Validator<CreateHabitDto>{

    @Override
    public ValidationResult isValid(CreateHabitDto habitDto) {
        ValidationResult validationResult = new ValidationResult();
        if(habitDto.getName().isEmpty()){
            validationResult.add(Error.of("invalid name","Invalid name of habit"));
        }
        if (habitDto.getDescription().isEmpty()){
            validationResult.add(Error.of("invalid description", "Invalid description"));
        }
        if(Frequency.find(habitDto.getFrequency()).isEmpty()){
            validationResult.add(Error.of("invalid frequency", "Invalid frequency"));
        }
        return validationResult;
    }
}