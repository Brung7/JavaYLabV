package by.vladimir.migration;

import by.vladimir.utils.ConnectionManager;
import by.vladimir.utils.PropertyUtils;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс для уприавления миграциями.
 */
@Component
@RequiredArgsConstructor
public class LiquibaseMigration {

    private final ConnectionManager connectionManager;

    private final PropertyUtils propertyUtils;

    /**
     * Подключается к базе данных.
     * Выполняет миграции.
     * Выводит в консоль сообщение о выполнени миграций
     * или выводит исключение.
     */
    public void runMigration() {
        Properties properties = propertyUtils.readYamlFile();
        String changeLogFilePath = properties.getProperty("liquibase.changelogFilePath");


        try (Connection connection = connectionManager.get()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(changeLogFilePath, new ClassLoaderResourceAccessor(), database);
            liquibase.update();

            System.out.println("Migrations is completed successfully ");

        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}