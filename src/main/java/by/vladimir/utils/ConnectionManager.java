package by.vladimir.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import liquibase.database.DatabaseConnection;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Класс для подключения к базе данных.
 */
@Component
public class ConnectionManager {

    private PropertyUtils propertyUtils;

    @Autowired
    public ConnectionManager(PropertyUtils propertyUtils) {
        this.propertyUtils = propertyUtils;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Неудалось загрузить драйвер PostgreSQL", e);
        }
    }

    /**
     * Ссылка для подключения к базе данных.
     */
    private String url;

    /**
     * Имя пользователя базы данных.
     */
    private String username;

    /**
     * Пароль для подключения к базе данных.
     */
    private String password;

    /**
     * Получает из Properties данные для подключения к базе данных
     * и возвращает connection.
     * @return connection к базе данных.
     * @throws SQLException
     */
    public Connection get() throws SQLException {
        Properties properties = propertyUtils.readYamlFile();
        url = properties.getProperty("database.url");
        username = properties.getProperty("database.username");
        password = properties.getProperty("database.password");
        return DriverManager.getConnection(url,username,password);
    }
}