package by.vladimir.validator;

import by.vladimir.dto.CreateDateOfComplDto;
import org.springframework.stereotype.Component;

@Component
public class CreateDateValidator implements Validator<CreateDateOfComplDto>{

    @Override
    public ValidationResult isValid(CreateDateOfComplDto dateOfComplDto) {
        ValidationResult validationResult = new ValidationResult();
        if(dateOfComplDto.getDate().isEmpty()){
            validationResult.add(Error.of("date invalid","Date invalid"));
            throw new IllegalArgumentException("Invalid date");
        }
        return validationResult;
    }
}