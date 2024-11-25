package by.vladimir.validator;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.service.implementation.Filter;
import by.vladimir.service.implementation.HabitServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateHabitValidator implements Validator<CreateHabitDto>{

    private final Filter filter;

    @Override
    public ValidationResult isValid(CreateHabitDto habitDto) {
        ValidationResult validationResult = new ValidationResult();
        if(habitDto.getName().isEmpty()){
            validationResult.add(Error.of("invalid name","Invalid name of habit"));
            throw new IllegalArgumentException("Invalid name");

        }
        if (habitDto.getDescription().isEmpty()){
            validationResult.add(Error.of("invalid description", "Invalid description"));
            throw new IllegalArgumentException("Invalid description");

        }
        if(filter.findFrequency(habitDto.getFrequency()).isEmpty()){
            validationResult.add(Error.of("invalid frequency", "Invalid frequency"));
            throw new IllegalArgumentException("Invalid description");

        }
        return validationResult;
    }
}