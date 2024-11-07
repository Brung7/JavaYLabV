package by.vladimir.servlet.UserServlet;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class ShowAllServletTest {

    @Container
    private static final GenericContainer<?> servletContainer = new GenericContainer<>("tomcat:10.1.19")
            .withExposedPorts(8080)
            .withEnv("JAVA_OPTS", "-Djava.awt.headless=true");

    @Test
    public void testShowAllServlet() throws Exception {
        URL url = new URL("http://" + servletContainer.getHost() + ":" + servletContainer.getMappedPort(8080) + "/JavaEE/listOfAll");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        assertEquals(HttpServletResponse.SC_OK, responseCode);

        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        connection.disconnect();
    }
}