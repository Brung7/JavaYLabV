package by.vladimir.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Setter
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