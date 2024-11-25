package by.audit.entity;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Audit {
    private Long id;
    private String username;
    private String methodName;
    private LocalDateTime time;
    private String actionResult;
}