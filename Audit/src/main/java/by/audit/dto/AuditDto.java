package by.audit.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDto {
    private String username;
    private String methodName;
    private LocalDateTime time;
    private String actionResult;
}