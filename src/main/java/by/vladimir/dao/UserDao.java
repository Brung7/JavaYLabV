package by.vladimir.dao;

import by.vladimir.dto.HabitDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.utils.ConnectionManager;

import java.sql.*;
import java.util.*;

public class UserDao {
    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO main.users (email, password, role) VALUES (?,?,?)
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, email, password, role FROM main.users
            """;
    private static final String FIND_BY_EMAIL_SQL = """
            SELECT id, email, password, role FROM main.users WHERE email=?
            """;
    private static final String FIND_BY_ID_SQL= """
            SELECT * FROM main.users WHERE id=?
            """;
    private static final String DELETE_SQL= """
            DELETE FROM main.users WHERE id=?
            """;
    private static final String UPDATE_SQL= """
            UPDATE main.users
            SET email=?,
            password=?,
            role=?
            WHERE id=?
            """;
    private static final String FIND_ALL_USERS_ALL_HABITS = """
            SELECT u.id AS user_id,u.email AS email, h.id AS habit_id,
             h.name AS name, h.description AS description, h.frequency AS frequency
            FROM main.users u
            LEFT JOIN main.habits h on u.id = h.user_id
            """;

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public User save(User user) {
        try(Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
        statement.setString(1,user.getEmail());
        statement.setString(2, user.getPassword());
        statement.setString(3,user.getRole().name());
        statement.executeUpdate();
        ResultSet key = statement.getGeneratedKeys();
        key.next();
        user.setId(key.getObject("id", Long.class));
        return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findByEmail(String email) {
        try (Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            Optional<User> user = Optional.empty();
            if (result.next()) {
                user = Optional.ofNullable(buildUser(result));
            }
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User buildUser(ResultSet result) throws SQLException {
        return new User(
                result.getLong("id"),
                result.getString("email"),
                result.getString("password"),
                Role.valueOf(result.getString("role")));
    }

    public void update(User user) {
        try (Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)){
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3,user.getRole().name());
            statement.setLong(4,user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getListOfUsers() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<User> userList = new ArrayList<>();
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                userList.add(buildUser(result));
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<UserDto> getAllHabitsOfAllUsers(){
        List<UserDto> users = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS_ALL_HABITS)) {
            ResultSet result = statement.executeQuery();
            UserDto userDto = null;
            while (result.next()) {
                Long userId = result.getLong("user_id");
                if (userDto == null|| !Objects.equals(userDto.getId(), userId)) {
                    if(userDto !=null){
                        users.add(userDto);
                    }
                    userDto = new UserDto();
                    userDto.setId(userId);
                    userDto.setEmail(result.getString("email"));
                    userDto.setHabitList(new ArrayList<>());
                }
                Long habitId = result.getLong("habit_id");
                if(habitId !=0) {
                    HabitDto habitDto = new HabitDto();
                    habitDto.setId(result.getLong("habit_id"));
                    habitDto.setName(result.getString("name"));
                    habitDto.setDescription(result.getString("description"));
                    habitDto.setFrequency(Frequency.valueOf(result.getString("frequency")));
                    userDto.getHabitList().add(habitDto);
                }
            }
            if(userDto!=null){
                users.add(userDto);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
