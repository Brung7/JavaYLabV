package by.vladimir.mapper;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Habit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для маппинга Dto привычек.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HabitMapper {

    /**
     * Получает объект класса CreateHabitDto.
     * Мапит CreateHabitDto в Habit
     * @param createHabitDto - Dto получаемой привычки.
     * @return объект класса Habit.
     */
    @Mapping(source = "name",target = "name")
    @Mapping(source = "description",target = "description")
    @Mapping(source = "frequency",target = "frequency")
    @Mapping(source = "userId",target = "userId")
    Habit toHabit(CreateHabitDto createHabitDto);

    /**
     * Получает на вход HabitDto.
     * Мапит HabitDto в Habit.
     * @param habitDto - Dto получаемой привычки.
     * @return объект класса Habit.
     */
    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name")
    @Mapping(source = "description",target = "description")
    @Mapping(source = "frequency",target = "frequency")
    Habit toHabitFromHabitDto(HabitDto habitDto);

}