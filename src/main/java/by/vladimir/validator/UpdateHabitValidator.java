package by.vladimir.validator;

import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Frequency;

public class UpdateHabitValidator implements Validator<HabitDto>{

    private static final UpdateHabitValidator INSTANCE = new UpdateHabitValidator();

    public static UpdateHabitValidator getInstance(){
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(HabitDto habitDto) {
        ValidationResult validationResult = new ValidationResult();
        if(habitDto.getName().isEmpty()){
            validationResult.add(Error.of("invalid name","Invalid name"));
        }
        if(habitDto.getDescription().isEmpty()){
            validationResult.add(Error.of("invalid description","Invalid description"));

        }
        if(habitDto.getFrequency().name().isEmpty()){
            validationResult.add(Error.of("invalid frequency","Invalid frequency"));
        }
        return validationResult;
    }
}