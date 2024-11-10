package by.vladimir.dao;

import by.vladimir.entity.DateOfCompletion;
import by.vladimir.utils.ConnectionManager;
import by.vladimir.utils.DateFormatter;
import by.vladimir.utils.SqlQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Класс для взаимодейсвия с таблицей date в базе данных
 */
@Repository
@RequiredArgsConstructor
public class DateOfCompletionDao {

    private final ConnectionManager connectionManager;

    private final DateFormatter dateFormatter;

    private final SqlQueries sqlQueries;

    /**
     * Метод принимает объект класса DateOfCompletion.
     * Подключается к базе данных.
     * Если подключение успешно устанавливаются значения даты и id привычки.
     * Генерируется id и при помощи SQL запроса создается запись в базе данных.
     * @param date объект класса DateOfCompletion
     * @return возвращет созданный объект
     */
    public DateOfCompletion save(DateOfCompletion date){
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement(sqlQueries.getSAVE_DATE(), Statement.RETURN_GENERATED_KEYS)){
            statement.setDate(1, dateFormatter.convertSqlToUtil(date.getDate()));
            statement.setLong(2,date.getHabitId());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            date.setId(key.getObject("id", Long.class));
            return date;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод принимает объект класса DateOfCompletion.
     * Подключается к базе данных.
     * Если подключение успешно при помощи SQL запроса обновляется запись в базе данных
     * @param date объект класса DateOfCompletion
     */
    public void update(DateOfCompletion date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(sqlQueries.getUPDATE_DATE())){
            statement.setDate(1,dateFormatter.convertSqlToUtil(date.getDate()));
            statement.setLong(2,date.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получает на вход id даты.
     * При помощи SQL-запроса удаляет запись из базы данных по полученному id.
     * @param id - индификатор записи в базе данных
     */
    public void delete(Long id){
        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(sqlQueries.getDELETE_DATE())){
            statement.setLong(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получет на вход id habit, к которой относится date.
     * При помощи SQL-запроса находит все date с заданным habitId.
     * Добавляе их в List
     * @param habitId - индификатор Habit, к которой относятся даты
     * @return возвращает лист с датами, которые относятся к habit с заданным id
     */
    public List<DateOfCompletion> findByHabitId(Long habitId){
        List<DateOfCompletion> listDate = new ArrayList<>();
        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(sqlQueries.getFIND_BY_HABIT_ID())){
            statement.setLong(1,habitId);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                listDate.add(builder(result));
            }
            return listDate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Получает на вход resultSet.
     * Создает объект DateOfCompletion по полученным значениям из базы данных.
     * @param resultSet - результат запроса полученный из базы данных.
     * @return возвращает объект DateOfCompletion
     * @throws SQLException
     */
    public DateOfCompletion builder(ResultSet resultSet) throws SQLException {
        return new DateOfCompletion(
                resultSet.getLong("id"),
                resultSet.getLong("habit_id"),
                resultSet.getDate("completion_date")
        );
    }

    /**
     * Поулчает на вход id даты.
     * При помощи SQL-запроса находит запись в базе данных по заданному id.
     * Записывает в Optional переменную полученный объект.
     * @param id - индификатор date
     * @return - возвращает Optional объекта
     */
    public Optional<DateOfCompletion> findById(Long id){
        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(sqlQueries.getFIND_BY_ID_HABIT())){
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            Optional<DateOfCompletion> optionalDateOfCompletion = Optional.empty();
            if(result.next()){
                optionalDateOfCompletion = Optional.ofNullable(builder(result));
            }
            return optionalDateOfCompletion;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
