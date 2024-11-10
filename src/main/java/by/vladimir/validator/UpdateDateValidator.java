package by.vladimir.validator;

import by.vladimir.dto.DateOfCompletionDto;
import org.springframework.stereotype.Component;

@Component
public class UpdateDateValidator implements Validator<DateOfCompletionDto>{

    @Override
    public ValidationResult isValid(DateOfCompletionDto dateOfCompletionDto) {
        ValidationResult validationResult = new ValidationResult();
        if(dateOfCompletionDto.getDate().isEmpty()){
            validationResult.add(Error.of("invalid date","Invalid date"));
            throw new IllegalArgumentException("Invalid date");
        }
        return validationResult;
    }
}