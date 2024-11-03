package by.vladimir.migration;

import by.vladimir.utils.ConnectionManager;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс для уприавления миграциями.
 */
public class LiquibaseMigration {

    /**
     * Подключается к базе данных.
     * Выполняет миграции.
     * Выводит в консоль сообщение о выполнени миграций
     * или выводит исключение.
     */
    public static void runMigration() {

        try (Connection connection = ConnectionManager.get()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db.changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();

            System.out.println("Migrations is completed successfully ");

        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}