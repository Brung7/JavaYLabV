package by.vladimir.servlet.HabitServlet;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class AddHabitServletTest {

    @Container
    private static final GenericContainer<?> servletContainer = new GenericContainer<>("tomcat:10.1.19")
            .withExposedPorts(8080)
            .withEnv("JAVA_OPTS", "-Djava.awt.headless=true");


    @Test
    public void testAddHabitServlet() throws Exception {

        URL url = new URL("http://" + servletContainer.getHost() + ":" + servletContainer.getMappedPort(8080) + "/JavaEE/newHabit");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String requestBody = "{\"name\": \"Read\", \"description\": \"Education\", \"frequency\": \"DAILY\"}";
        connection.getOutputStream().write(requestBody.getBytes());

        int responseCode = connection.getResponseCode();
        assertEquals(HttpServletResponse.SC_OK, responseCode);

        connection.disconnect();
    }
}