package by.vladimir.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDateOfComplDto {
    Long id;
    Long habitId;
    String date;

}
