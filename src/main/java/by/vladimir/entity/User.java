package by.vladimir.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    /**
     * Инидификатор пользователя
     */
    Long id;

    /**
     * Адресс электронной почты
     */
    String email;

    /**
     * Пароль пользователя
     */
    String password;

    /**
     * Роль пользователя
     */
    Role role;
}