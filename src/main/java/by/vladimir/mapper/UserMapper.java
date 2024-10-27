package by.vladimir.mapper;

import by.vladimir.dto.CreateUserDto;
import by.vladimir.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для маппинга Dto пользователя.
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Получает на вход CreateUserDto
     * Мапит объект класса CreateUserDto в User.
     * @param createUserDto - dto получаемого пользователя.
     * @return объект класса User.
     */
    User toUser(CreateUserDto createUserDto);
}
