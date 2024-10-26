package by.vladimir.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateHabitDto {
    String name;
    String description;
    String frequency;
    Long userId;
}
