package by.vladimir.mapper;

import by.vladimir.dto.CreateUserDto;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;

public class UserMapper {
    private static final UserMapper INSTANCE = new UserMapper();

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }

    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .role(Role.valueOf(createUserDto.getRole()))
                .build();
    }

}
