package by.vladimir.servlet.UserServlet;

import by.vladimir.annotation.Audit;
import by.vladimir.annotation.Loggable;
import by.vladimir.dto.UserDto;
import by.vladimir.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Audit
@WebServlet("/listOfAll")
public class ShowAllServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserDto> userDtoList = userService.getAllUsersWithHabits();
        String jsonResponse = objectMapper.writeValueAsString(userDtoList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonResponse);
    }
}