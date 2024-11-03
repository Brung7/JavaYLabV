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
public class AddDateOfCompletionServletTest {
    @Container
    private static final GenericContainer<?> servletContainer = new GenericContainer<>("tomcat:10.1.19")
            .withExposedPorts(8080)
            .withEnv("JAVA_OPTS", "-Djava.awt.headless=true")
            .withLogConsumer(outputFrame -> System.out.print(outputFrame.getUtf8String()));


    private String servletUrl;

    @BeforeEach
    public void setup() {
        servletUrl = "http://" + servletContainer.getHost() + ":" + servletContainer.getMappedPort(8080) + "/JavaEE/newDate";
    }

    @Test
    public void testDoPost() throws Exception {
        URL url = new URL(servletUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String requestBody = "{\"date\": \"2024-10-26\", \"habitId\": \"2\"}";
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        assertEquals(HttpServletResponse.SC_OK, responseCode);

        connection.disconnect();
    }
}