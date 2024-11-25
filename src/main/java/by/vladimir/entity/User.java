package by.vladimir.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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