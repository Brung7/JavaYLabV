package by.vladimir.validator;

import by.vladimir.dto.HabitDto;
import org.springframework.stereotype.Component;

@Component
public class UpdateHabitValidator implements Validator<HabitDto>{

    @Override
    public ValidationResult isValid(HabitDto habitDto) {
        ValidationResult validationResult = new ValidationResult();
        if(habitDto.getName().isEmpty()){
            validationResult.add(Error.of("invalid name","Invalid name"));
            throw new IllegalArgumentException("Invalid name of habit");
        }
        if(habitDto.getDescription().isEmpty()){
            validationResult.add(Error.of("invalid description","Invalid description"));
            throw new IllegalArgumentException("Invalid description");
        }
        if(habitDto.getFrequency().name().isEmpty()){
            validationResult.add(Error.of("invalid frequency","Invalid frequency"));
            throw new IllegalArgumentException("Invalid frequency");

        }
        return validationResult;
    }
}