package by.vladimir.validator;

import by.vladimir.dto.CreateDateOfComplDto;

public class CreateDateValidator implements Validator<CreateDateOfComplDto>{

    private static final CreateDateValidator INSTANCE = new CreateDateValidator();

    public static CreateDateValidator getInstance(){
        return INSTANCE;
    }
    @Override
    public ValidationResult isValid(CreateDateOfComplDto dateOfComplDto) {
        ValidationResult validationResult = new ValidationResult();
        if(dateOfComplDto.getDate().isEmpty()){
            validationResult.add(Error.of("date invalid","Date invalid"));
        }
        return validationResult;
    }
}