package by.vladimir.dto;

import by.vladimir.entity.Frequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitDto {
    Long id;
    String name;
    String description;
    Frequency frequency;

}
