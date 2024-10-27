package by.vladimir.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Habit {

    /**
     * Индификатор привычки
     */
    Long id;

    /**
     * Название привычки
     */
    String name;

    /**
     * Описание привычки
     */
    String description;

    /**
     * Частота выполнения
     */
    Frequency frequency;

    /**
     * индификатор пользователя
     */
    Long userId;
}