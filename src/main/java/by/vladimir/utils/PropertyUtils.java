package by.vladimir.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Класс для чтения файлов yml.
 */
@Component
public class PropertyUtils {

    /**
     * Мето читает файл yml и возвращает его содержимое в виде объекта Properties.
     * @return Объект Properties.
     */
    public Properties readYamlFile() {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(new ClassPathResource("application.yml"));
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}