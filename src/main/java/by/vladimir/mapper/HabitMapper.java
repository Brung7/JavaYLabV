package by.vladimir.mapper;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Habit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для маппинга Dto привычек.
 */
@Mapper
public interface HabitMapper {

    HabitMapper INSTANCE = Mappers.getMapper(HabitMapper.class);

    /**
     * Получает объект класса CreateHabitDto.
     * Мапит CreateHabitDto в Habit
     * @param createHabitDto - Dto получаемой привычки.
     * @return объект класса Habit.
     */
    Habit toHabit(CreateHabitDto createHabitDto);

    /**
     * Получает на вход HabitDto.
     * Мапит HabitDto в Habit.
     * @param habitDto - Dto получаемой привычки.
     * @return объект класса Habit.
     */
    Habit toHabitFromHabitDto(HabitDto habitDto);

}
