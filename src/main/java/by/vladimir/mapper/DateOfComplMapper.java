package by.vladimir.mapper;

import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.utils.DateFormatter;

public class DateOfComplMapper {
    private static final DateOfComplMapper INSTANCE = new DateOfComplMapper();

    private DateOfComplMapper() {
    }

    public static DateOfComplMapper getInstance() {
        return INSTANCE;
    }

    DateFormatter formatter = DateFormatter.getInstance();

    public DateOfCompletion mapFrom(CreateDateOfComplDto dateDto) {
        return DateOfCompletion.builder()
                .habitId(dateDto.getHabitId())
                .date(formatter.formatterStrToDate(dateDto.getDate()))
                .build();

    }
}
