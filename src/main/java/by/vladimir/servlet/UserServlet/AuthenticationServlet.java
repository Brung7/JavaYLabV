package by.vladimir.servlet.UserServlet;

import by.vladimir.annotation.Loggable;
import by.vladimir.entity.User;
import by.vladimir.service.UserService;
import by.vladimir.utils.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@Loggable
@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {

    private  final UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(req.getReader());
        String email = jsonNode.get("email").asText();
        String password = jsonNode.get("password").asText();

        Optional<User> optionalUser = userService.authentication(email);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.getPassword().equals(password)){
                String token = JwtUtil.generateToken(email);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write("{\"token\": \"" + token + "\"}");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("User authenticated successfully");
            }
            else{
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("Authentication failed");
            }
        }
    }
}