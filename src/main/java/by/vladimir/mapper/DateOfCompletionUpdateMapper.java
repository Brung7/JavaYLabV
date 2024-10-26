package by.vladimir.mapper;

import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.utils.DateFormatter;

public class DateOfCompletionUpdateMapper {
    private static final DateOfCompletionUpdateMapper INSTANCE = new DateOfCompletionUpdateMapper();

    private DateOfCompletionUpdateMapper() {
    }

    public static DateOfCompletionUpdateMapper getInstance() {
        return INSTANCE;
    }

    DateFormatter formatter = DateFormatter.getInstance();

    public DateOfCompletion mapFrom(DateOfCompletionDto dateDto) {
        return DateOfCompletion.builder()
                .id(dateDto.getId())
                .date(formatter.formatterStrToDate(dateDto.getDate()))
                .build();
    }
}
