package by.vladimir.migration;

import by.vladimir.utils.ConnectionManager;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Класс для уприавления миграциями.
 */
public class LiquibaseMigration {

    private static String changeLogPath;

    public LiquibaseMigration(){
        readChangelogFilePathFromYaml();
    }
    /**
     * Метод читает файл application.yml.
     * Берет из него changelogFilePath.
     * @return changelogFilePath.
     */
    public void readChangelogFilePathFromYaml() {
        Yaml yaml = new Yaml();
        try (InputStream in = LiquibaseMigration.class.getClassLoader().getResourceAsStream("application.yml")) {
            Map<String, Object> yamlData = yaml.load(in);
            Map<String, String> yamlConf = (Map<String, String>) yamlData.get("liquibase");
            this.changeLogPath = yamlConf.get("changelogFilePath");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Подключается к базе данных.
     * Выполняет миграции.
     * Выводит в консоль сообщение о выполнени миграций
     * или выводит исключение.
     */
    public static void runMigration() {

        ConnectionManager connectionManager = new ConnectionManager();
        String changeLogFilePath = changeLogPath;

        try (Connection connection = connectionManager.get()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(changeLogFilePath, new ClassLoaderResourceAccessor(), database);
            liquibase.update();

            System.out.println("Migrations is completed successfully ");

        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}