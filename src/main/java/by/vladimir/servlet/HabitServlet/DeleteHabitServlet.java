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

@Audit
@WebServlet("/deleteHabit")
public class DeleteHabitServlet extends HttpServlet {

    private final HabitService habitService = HabitService.getInstance();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Habit habit = objectMapper.readValue(req.getReader(), Habit.class);
        try {
            habitService.delete(habit.getId());
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Habit delete successfully");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Delete failed");
        }
    }
}