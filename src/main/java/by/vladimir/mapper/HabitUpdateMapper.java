package by.vladimir.mapper;

import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Habit;

public class HabitUpdateMapper {
    private static final HabitUpdateMapper INSTANCE = new HabitUpdateMapper();

    private HabitUpdateMapper() {
    }

    public static HabitUpdateMapper getINSTANCE() {
        return INSTANCE;
    }

    public Habit mapFrom(HabitDto habitDto) {
        return Habit.builder()
                .id(habitDto.getId())
                .name(habitDto.getName())
                .description(habitDto.getDescription())
                .frequency(habitDto.getFrequency())
                .build();
    }
}
