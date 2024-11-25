package by.vladimir.mapper;

import by.vladimir.dto.CreateUserDto;
import by.vladimir.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Интерфейс для маппинга Dto пользователя.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    /**
     * Получает на вход CreateUserDto
     * Мапит объект класса CreateUserDto в User.
     * @param createUserDto - dto получаемого пользователя.
     * @return объект класса User.
     */
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "role", target = "role")
    User toUser(CreateUserDto createUserDto);
}