package by.vladimir.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateOfCompletion {
    private Long id;
    private Long habitId;
    private Date date;

}
