package by.vladimir.validator;

import by.vladimir.dto.CreateUserDto;
import by.vladimir.service.implementation.Filter;
import by.vladimir.service.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserValidator implements Validator<CreateUserDto> {

    private final Filter filter;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,15}$";

    @Override
    public ValidationResult isValid(CreateUserDto userDto) {

        ValidationResult validationResult = new ValidationResult();

        if (!userDto.getEmail().matches(EMAIL_REGEX)) {
            validationResult.add(Error.of("invalid email", "Invalid email"));
            throw new IllegalArgumentException("Invalid email");
        }
        if (!userDto.getPassword().matches(PASSWORD_REGEX)) {
            validationResult.add(Error.of("invalid password", "Invalid password"));
            throw new IllegalArgumentException("Invalid password");

        }
        if (filter.findRole(userDto.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid role", "Invalid role"));
            throw new IllegalArgumentException("Invalid role");

        }
        return validationResult;
    }

}