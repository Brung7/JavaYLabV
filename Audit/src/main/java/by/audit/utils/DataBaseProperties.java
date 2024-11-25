package by.audit.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@RequiredArgsConstructor
@Getter
public class DataBaseProperties {

    private final PropertyUtils propertyUtils;

    private String url;

    private String username;

    private String password;

    public void readDbProperties(){
        Properties properties = propertyUtils.readYamlFile();
        url = properties.getProperty("database.url");
        username = properties.getProperty("database.username");
        password = properties.getProperty("database.password");
    }
}