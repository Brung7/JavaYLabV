package by.vladimir.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class Habit {
    private Long id;
    private String name;
    private String description;
    private Frequency frequency;
    private Long userId;

}
