package by.vladimir.mapper;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;

public class HabitMapper {
    private static final HabitMapper INSTANCE = new HabitMapper();

    private HabitMapper() {
    }

    public static HabitMapper getInstance() {
        return INSTANCE;
    }

    public Habit mapFrom(CreateHabitDto createHabitDto) {
        return Habit.builder()
                .name(createHabitDto.getName())
                .description(createHabitDto.getDescription())
                .frequency(Frequency.valueOf(createHabitDto.getFrequency()))
                .userId(createHabitDto.getUserId())
                .build();
    }
}
