package by.audit.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Класс для подключения к базе данных.
 */
@Component
public class ConnectionManager {

    private final DataBaseProperties dataBaseProperties;

    @Autowired
    public ConnectionManager(DataBaseProperties dataBaseProperties) {
        this.dataBaseProperties = dataBaseProperties;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Неудалось загрузить драйвер PostgreSQL", e);
        }
    }

    /**
     * Получает из Properties данные для подключения к базе данных
     * и возвращает connection.
     * @return connection к базе данных.
     * @throws SQLException
     */
    public Connection get() throws SQLException {
        dataBaseProperties.readDbProperties();
        return DriverManager.getConnection(dataBaseProperties.getUrl(),dataBaseProperties.getUsername(),dataBaseProperties.getPassword());
    }
}