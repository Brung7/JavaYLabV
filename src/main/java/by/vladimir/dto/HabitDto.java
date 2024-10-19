package by.vladimir.dto;

import by.vladimir.entity.Frequency;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitDto {
    Long id;
    String name;
    String description;
    Frequency frequency;
}
