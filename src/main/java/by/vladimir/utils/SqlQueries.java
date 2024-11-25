package by.vladimir.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SqlQueries {

    private final String SAVE_USER = """
            INSERT INTO main.users (email, password, role) VALUES (?,?,?)
            """;

    private final String FIND_ALL_USERS = """
            SELECT id, email, password, role FROM main.users
            """;

    private final String FIND_BY_EMAIL_SQL = """
            SELECT id, email, password, role FROM main.users WHERE email=?
            """;

    private final String FIND_BY_ID_USER = """
            SELECT * FROM main.users WHERE id=?
            """;

    private final String DELETE_USER = """
            DELETE FROM main.users WHERE id=?
            """;

    private final String UPDATE_USER = """
            UPDATE main.users
            SET email=?,
            password=?,
            role=?
            WHERE id=?
            """;

    private final String FIND_ALL_USERS_ALL_HABITS = """
            SELECT u.id AS user_id,u.email AS email, h.id AS habit_id,
             h.name AS name, h.description AS description, h.frequency AS frequency
            FROM main.users u
            LEFT JOIN main.habits h on u.id = h.user_id
            """;

    private final String SAVE_HABIT = """
            INSERT INTO main.habits (name, description, frequency, user_id) VALUES (?,?,?,?)
            """;

    private final String UPDATE_HABIT = """
            UPDATE main.habits
            SET name=?,
            description=?,
            frequency=?
            WHERE id=?
            """;

    private final String DELETE_HABIT = """
            DELETE FROM main.habits WHERE id=?
            """;

    private final String FIND_BY_ID_HABIT = """
            SELECT id, name, description, frequency, user_id FROM main.habits WHERE id=?
            """;

    private final String FIND_BY_USERID_HABIT = """
            SELECT id, name, description, frequency, user_id FROM main.habits WHERE user_id=?
            """;

    private final String SAVE_DATE = """
            INSERT INTO main.dates (completion_date,habit_id) VALUES (?,?)
            """;

    private final String UPDATE_DATE = """
            UPDATE main.dates
            SET
            completion_date=?
            WHERE id=?
            """;

    private final String DELETE_DATE= """
            DELETE FROM main.dates WHERE id=?;
            """;

    private final String FIND_BY_ID_DATE = """
            SELECT id,habit_id,completion_date FROM main.dates WHERE id=?
            """;

    private final String FIND_BY_HABIT_ID = """
            SELECT id,habit_id,completion_date FROM main.dates WHERE habit_id=?
            """;
}