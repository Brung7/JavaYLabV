package by.audit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "starter-audit")
public class StarterProperties {
    private String author;
}
