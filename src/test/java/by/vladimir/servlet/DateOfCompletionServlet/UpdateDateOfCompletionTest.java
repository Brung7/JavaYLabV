package by.vladimir.servlet.DateOfCompletionServlet;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class UpdateDateOfCompletionTest {

    @Container
    private static final GenericContainer<?> servletContainer = new GenericContainer<>("tomcat:10.1.19")
            .withExposedPorts(8080)
            .withEnv("JAVA_OPTS", "-Djava.awt.headless=true");

    private String servletUrl;

    @BeforeEach
    public void setup() {
        servletUrl = "http://" + servletContainer.getHost() + ":" + servletContainer.getMappedPort(8080) + "/JavaEE/updateDate";
    }

    @Test
    public void testDoPut() throws Exception {
        URL url = new URL(servletUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String requestBody = "{\"id\": 1, \"newDate\": \"2024-10-26\"}";
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        assertEquals(HttpServletResponse.SC_OK, responseCode);

        connection.disconnect();
    }
}