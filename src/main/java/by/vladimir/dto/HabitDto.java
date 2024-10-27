package by.vladimir.dto;

import by.vladimir.entity.Frequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
