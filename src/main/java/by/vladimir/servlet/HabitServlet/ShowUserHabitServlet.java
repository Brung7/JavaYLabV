package by.vladimir.servlet.HabitServlet;

import by.vladimir.annotation.Audit;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.service.HabitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Audit
@WebServlet("/userHabits")
public class ShowUserHabitServlet extends HttpServlet {

    private final HabitService habitService = HabitService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(req.getReader(), User.class);
        List<Habit> habitList = habitService.getUserHabits(user);
        resp.getWriter().write(habitList.toString());
    }
}