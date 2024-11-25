package by.vladimir.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateHabitDto {
    String name;
    String description;
    String frequency;
    Long userId;

}
