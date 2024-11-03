package by.vladimir.servlet.HabitServlet;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class UpdateHabitServletTest {

    @Container
    private static final GenericContainer<?> servletContainer = new GenericContainer<>("tomcat:10.1.19")
            .withExposedPorts(8080)
            .withEnv("JAVA_OPTS", "-Djava.awt.headless=true");


    @Test
    public void testUpdateHabitServlet() throws Exception {
        URL url = new URL("http://" + servletContainer.getHost() + ":" + servletContainer.getMappedPort(8080) + "/JavaEE/updateHabit");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        String requestBody = "{\"id\": 123, \"name\": \"NewHabitName\", \"description\": \"Health\", \"frequency\": \"DAILY\"}";
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        assertEquals(HttpServletResponse.SC_OK, responseCode);

        connection.disconnect();
    }
}