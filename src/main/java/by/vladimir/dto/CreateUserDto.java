package by.vladimir.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDto {
     String email;
     String password;
     String role;
}
