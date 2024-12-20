package by.vladimir.dao;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.utils.ConnectionManager;
import by.vladimir.utils.SqlQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;



/**
 * Класс для взаимодействия с таблицей habit в базе данных
 */
@Repository
@RequiredArgsConstructor
public class HabitDao {

    private final ConnectionManager connectionManager;

    private final SqlQueries sqlQueries;





    /**
     * Принимает на вход объект класса Habit.
     * Подключается к базе данных.
     * Если подключение успешно устанавливаются значения полей, которые надо передать в базу данных.
     * Генерируется id.
     * При помощи SQL-запроса запись сохраняется в базу данных
     * @param habit - объект класса Habit
     * @return возвращет созданный объект
     */
    public Habit save(Habit habit){
        try(Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sqlQueries.getSAVE_HABIT(), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,habit.getName());
            statement.setString(2, habit.getDescription());
            statement.setString(3, (habit.getFrequency().name()));
            statement.setLong(4,habit.getUserId());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            habit.setId(key.getObject("id",Long.class));
            return habit;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получает на вход id привычки.
     * Подключается к базе данных.
     * Если подключение успешно передает id в SQL-запрос.
     * Удаляет запись из базы данных по заданному id.
     * @param id - индификатор привычки
     */
    public void delete(Long id){
        try(Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(sqlQueries.getDELETE_HABIT())) {
            statement.setLong(1,id);
        statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получает на вход id привычки.
     * Подключается к базе данных.
     * Если подключение успешно передает id в SQL-запрос.
     * При помощи SQL-запроса находит запись в базе данных по заданному id.
     * Записывает в Optional переменную полученный объект.
     * @param id - индификатор привычки.
     * @return возвращает Optional полученного объекта
     */
    public Optional<Habit> findById(Long id){
        try(Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(sqlQueries.getFIND_BY_ID_HABIT())) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Optional<Habit> habit = Optional.empty();
            if(result.next()){
                habit = Optional.ofNullable(builderHabit(result));
            }
            return habit;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получает на вход result.
     * Создает объект Habit по полученным значениям из базы данных.
     * @param result - результат запроса полученный из базы данных.
     * @return возвращает объект Habit
     * @throws SQLException
     */
    public Habit builderHabit(ResultSet result) throws SQLException {
        return new Habit(
                result.getLong("id"),
                result.getString("name"),
                result.getString("description"),
                Frequency.valueOf(result.getString("frequency")),
                result.getLong("user_id")
        );

    }

    /**
     * Получает на вход объект класса Habit.
     * Подключается к базе данных.
     * Если подключение успешно при помощи SQL-запроса обновляет запись в базе данных.
     * @param habit - объект класса Habit
     */

    public void update(Habit habit){
        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(sqlQueries.getUPDATE_HABIT())){
            statement.setString(1,habit.getName());
            statement.setString(2,habit.getDescription());
            statement.setString(3,habit.getFrequency().name());
            statement.setLong(4,habit.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получает на вход id пользователя, привычки которого хотим получить.
     * Подключается к базе данных.
     * Если подключение успешно передает id в SQL-запрос.
     * Создает объекты из полученных записей.
     * Добавляет объекты в лист.
     * @param userId - id пользователя.
     * @return возвращает лист полученных объектов.
     */
    public List<Habit> findByUserId(Long userId) {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement(sqlQueries.getFIND_BY_USERID_HABIT())) {
            statement.setLong(1, userId);
            List<Habit> habitList = new ArrayList<>();
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                habitList.add(builderHabit(result));
            }
            return habitList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
