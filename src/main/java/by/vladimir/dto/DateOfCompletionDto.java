package by.vladimir.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateOfCompletionDto {
    Long id;
    String date;

}
