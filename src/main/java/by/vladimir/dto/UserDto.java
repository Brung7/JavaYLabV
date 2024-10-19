package by.vladimir.dto;

import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    Long id;
    String email;
    List<HabitDto> habitList;
}
