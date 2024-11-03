package by.vladimir.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDto {
    private String username;
    private String methodName;
    private LocalDateTime time;
    private String actionResult;
}
