package by.vladimir.dao;

import by.vladimir.entity.DateOfCompletion;
import by.vladimir.utils.ConnectionManager;
import by.vladimir.utils.DateFormatter;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DateOfCompletionDao {
    private static final DateOfCompletionDao INSTANCE = new DateOfCompletionDao();
    private final DateFormatter dateFormatter = DateFormatter.getInstance();
    private static final String SAVE_SQL = """
            INSERT INTO main.dates (completion_date,habit_id) VALUES (?,?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE main.dates
            SET
            completion_date=?
            WHERE id=?
            """;
    private static final String DELETE_SQL= """
            DELETE FROM main.dates WHERE id=?;
            """;
    private static final String FIND_BY_ID = """
            SELECT id,habit_id,completion_date FROM main.dates WHERE id=?
            """;
    private static final String FIND_BY_HABIT = """
            SELECT id,habit_id,completion_date FROM main.dates WHERE habit_id=?
            """;
    public DateOfCompletion save(DateOfCompletion date){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
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
    public void update(DateOfCompletion date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)){
            statement.setDate(1,dateFormatter.convertSqlToUtil(date.getDate()));
            statement.setLong(2,date.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(Long id){
        try (Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(DELETE_SQL)){
            statement.setLong(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<DateOfCompletion> findByHabitId(Long habitId){
        List<DateOfCompletion> listDate = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(FIND_BY_HABIT)){
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

    public DateOfCompletion builder(ResultSet resultSet) throws SQLException {
        return new DateOfCompletion(
                resultSet.getLong("id"),
                resultSet.getLong("habit_id"),
                resultSet.getDate("completion_date")
        );
    }
    public Optional<DateOfCompletion> findById(Long id){
        try (Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
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
    private DateOfCompletionDao(){}
    public static DateOfCompletionDao getInstance(){
        return INSTANCE;
    }
}
