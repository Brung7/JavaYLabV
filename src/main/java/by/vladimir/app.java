package by.vladimir;

import by.audit.annotation.EnableAudit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAudit
public class app {
    public static void main(String[] args) {
        SpringApplication.run(app.class,args);
    }
}