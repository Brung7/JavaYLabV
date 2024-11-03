package by.vladimir.validator;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.DateOfCompletionDto;

public class UpdateDateValidator implements Validator<DateOfCompletionDto>{

    private static final UpdateDateValidator INSTANCE = new UpdateDateValidator();

    public static UpdateDateValidator getInstance(){
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(DateOfCompletionDto dateOfCompletionDto) {
        ValidationResult validationResult = new ValidationResult();
        if(dateOfCompletionDto.getDate().isEmpty()){
            validationResult.add(Error.of("invalid date","Invalid date"));
        }
        return validationResult;
    }
}