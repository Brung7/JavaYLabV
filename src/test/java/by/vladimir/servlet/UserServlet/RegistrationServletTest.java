package by.vladimir.servlet.UserServlet;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class RegistrationServletTest {

        @Container
        private static final GenericContainer<?> servletContainer = new GenericContainer<>("tomcat:10.1.19")
                .withExposedPorts(8080)
                .withEnv("JAVA_OPTS", "-Djava.awt.headless=true");

        private String servletUrl;

        @BeforeEach
        public void setup() {
            servletUrl = "http://" + servletContainer.getHost() + ":" + servletContainer.getMappedPort(8080) + "/JavaEE/registration";
        }

        @Test
        public void testDoPost() throws IOException {
            URL url = new URL(servletUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String jsonInputString = "{\"username\": \"vladimir@qwe.su\", \"password\": \"password123\", \"role\": \"USER\"}";
            connection.getOutputStream().write(jsonInputString.getBytes("UTF-8"));

            int responseCode = connection.getResponseCode();

            assertEquals(200, responseCode);
        }
}