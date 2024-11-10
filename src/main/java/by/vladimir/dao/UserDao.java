package by.vladimir.dao;

import by.vladimir.dto.HabitDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.utils.ConnectionManager;
import by.vladimir.utils.SqlQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;


/**
 * Класс для взаимодействия с таблицей users в базе данных
 */
@Repository
@RequiredArgsConstructor
public class UserDao {

    private final ConnectionManager connectionManager;

    private final SqlQueries sqlQueries;



    /**
     * Получает на вход пользователя.
     * Подключается к базе данных.
     * Если подключеие успешно, передает в SQL-запрос установленные поля.
     * Генерирует id.
     * Сохраняет запись в базу данных
     * @param user объект класса User, который хотим сохранить в базу данных
     * @return возвращает созданого пользователя
     */
    public User save(User user) {
        try(Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(sqlQueries.getSAVE_USER(), Statement.RETURN_GENERATED_KEYS)){
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

    /**
     * Принимает на вход id.
     * Создает подключение к базе данных.
     * Если подключение успешно, при помощи SQL-запроса находит запись в базе данных
     * и передает её в Optional переменную.
     * @param id - индификатор пользователя.
     * @return возвращает Optional полученного объекта.
     */
    public Optional<User> findById(Long id){
        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(sqlQueries.getFIND_BY_ID_USER())){
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            Optional<User> user = Optional.empty();
            if(result.next()){
                user = Optional.ofNullable(buildUser(result));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Принимает на вход email.
     * Создает подключение к базе данных.
     * Если подключение успешно, при помощи SQL-запроса находит запись в базе данных
     * и передает её в Optional переменную.
     * @param email - email пользователя, которого хотим найти
     * @return возвращает Optional полученного объекта
     */
    public Optional<User> findByEmail(String email) {
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sqlQueries.getFIND_BY_EMAIL_SQL())) {
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

    /**
     * Получает на вход result.
     * По полученым данным создает объект класса User.
     * @param result - результат запроса полученный из базы данных.
     * @return возвращает созданного пользователя
     * @throws SQLException
     */
    public User buildUser(ResultSet result) throws SQLException {
        return new User(
                result.getLong("id"),
                result.getString("email"),
                result.getString("password"),
                Role.valueOf(result.getString("role")));
    }

    /**
     * Получает на вход пользователя.
     * Подкючается к базе данных.
     * Если подключение успешно обновляет полученного пользователя в базе данных.
     * @param user - пользователь, которого хотим обновить
     */
    public void update(User user) {
        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(sqlQueries.getUPDATE_USER())){
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3,user.getRole().name());
            statement.setLong(4,user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получаето на вход id пользователя.
     * Создает подключение к базе данных.
     * Если подключение успешно, удаляет запись из базы данных
     * по полученному id.
     * @param id - индификатор пользователя, которого надо удалить
     */
    public void delete(Long id) {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement(sqlQueries.getDELETE_USER())) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Создает подключение к базе данных.
     * Если подключение успешно, возвращает список всех пользователей.
     * @return возвращает список полученных пользователей
     */
    public List<User> getListOfUsers() {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement(sqlQueries.getFIND_ALL_USERS())) {
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

    /**
     * Создает подключение к базе данных.
     * Если подключение получает пользователя,
     * затем получает все привычки, которые относятся к пользователю и добавляет
     * все привычки в список, для пользователя и добавляет самого пользователя в список.
     * @return возвращает список пользователей и список привычек каждого пользователя
     */
    public List<UserDto> getAllHabitsOfAllUsers(){
        List<UserDto> users = new ArrayList<>();
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement(sqlQueries.getFIND_ALL_USERS_ALL_HABITS())) {
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