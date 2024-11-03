package by.vladimir.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import liquibase.database.DatabaseConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.context.annotation.Configuration;
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

@Component
public class ConnectionManager {
    private String url;
    private String username;
    private String password;

    static {
        loadDriver();
    }
    public ConnectionManager(){
        loadDatabaseConfig();
    }
    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load PostgreSQL driver", e);
        }
    }
    public void loadDatabaseConfig() {
        Yaml yaml = new Yaml();
        try (InputStream in = ConnectionManager.class.getClassLoader().getResourceAsStream("application.yml")) {
            Map<String, Object> config = yaml.load(in);
            Map<String, String> databaseConfig = (Map<String, String>) config.get("database");
            this.url = databaseConfig.get("url");
            this.username = databaseConfig.get("username");
            this.password = databaseConfig.get("password");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Неудалось загрузить конфигурацию базы данных", e);
        }
    }

    public Connection get() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }
}