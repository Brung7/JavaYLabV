package by.vladimir.migration;

import by.vladimir.configure.AppConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Класс реализует интерфейс ServletContextListener и предоставляет возможность
 * выполнения миграций базы данных при инициализации контекста приложения.
 */

public class MigrationListener implements ServletContextListener {


    /**
     * Метод, вызывается при инициализации контекста приложения, который запускает миграции базы данных
     * с использованием LiquibaseMigration.
     *
     * @param sce Событие, связанное с инициализацией контекста сервлета
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setServletContext(servletContext);
        context.scan("by.vladimir");
        context.refresh();
        LiquibaseMigration liquibaseMigration = context.getBean(LiquibaseMigration.class);
        liquibaseMigration.runMigration();
    }
}