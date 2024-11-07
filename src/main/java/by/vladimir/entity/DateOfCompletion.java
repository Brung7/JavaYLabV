package by.vladimir.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateOfCompletion {

    /**
     * индификатор даты вывполнения
     */
    Long id;
    /**
     * индификатор привычки
     */
    Long habitId;
    /**
     * Дата выполнения привычки
     */
    Date date;
}