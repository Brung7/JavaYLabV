package by.vladimir.dao;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.utils.ConnectionManager;

import java.sql.*;
import java.util.*;

public class HabitDao {
    private static final HabitDao INSTANCE = new HabitDao();

    private static final String SAVE_SQL = """
            INSERT INTO main.habits (name, description, frequency, user_id) VALUES (?,?,?,?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE main.habits
            SET name=?,
            description=?,
            frequency=?
            WHERE id=?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM main.habits WHERE id=?
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, name, description, frequency, user_id FROM main.habits WHERE id=?
            """;
    private static final String FIND_BY_USERID_SQL = """
            SELECT id, name, description, frequency, user_id FROM main.habits WHERE user_id=?
            """;

    public Habit save(Habit habit){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
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

    public void delete(Long id){
        try(Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1,id);
        statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Habit> findById(Long id){
        try(Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
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
    public Habit builderHabit(ResultSet result) throws SQLException {
        return new Habit(
                result.getLong("id"),
                result.getString("name"),
                result.getString("description"),
                Frequency.valueOf(result.getString("frequency")),
                result.getLong("user_id")
        );

    }

    public void update(Habit habit){
        try (Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)){
            statement.setString(1,habit.getName());
            statement.setString(2,habit.getDescription());
            statement.setString(3,habit.getFrequency().name());
            statement.setLong(4,habit.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Habit> findByUserId(Long userId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USERID_SQL)) {
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

    private HabitDao(){
    }
    public static HabitDao getInstance(){
        return INSTANCE;
    }

}
