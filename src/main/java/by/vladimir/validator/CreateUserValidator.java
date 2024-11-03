package by.vladimir.validator;

import by.vladimir.dto.CreateUserDto;
import by.vladimir.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class CreateUserValidator implements Validator<CreateUserDto> {

    @Override
    public ValidationResult isValid(CreateUserDto userDto) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        String passwordRegex = "^[a-zA-Z0-9]{6,15}$";

        ValidationResult validationResult = new ValidationResult();

        if (!userDto.getEmail().matches(emailRegex)) {
            validationResult.add(Error.of("invalid email", "Invalid email"));
            System.out.println("Invalid email");
        }
        if (!userDto.getPassword().matches(passwordRegex)) {
            validationResult.add(Error.of("invalid password", "Invalid password"));
            System.out.println("Invalid pass");

        }
        if (Role.find(userDto.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid role", "Invalid role"));
            System.out.println("Invalid role");

        }
        return validationResult;
    }

}