package by.vladimir.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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