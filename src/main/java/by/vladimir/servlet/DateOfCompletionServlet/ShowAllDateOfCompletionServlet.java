package by.vladimir.servlet.DateOfCompletionServlet;

import by.vladimir.annotation.Audit;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.entity.Habit;
import by.vladimir.service.DateOfCompletionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Audit
@WebServlet("/allDate")
public class ShowAllDateOfCompletionServlet extends HttpServlet {

    private final DateOfCompletionService dateOfCompletionService = DateOfCompletionService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Habit habit = objectMapper.readValue(req.getReader(),Habit.class);
        try {
            List<DateOfCompletion> dateOfCompletionList = dateOfCompletionService.findByHabitId(habit.getId());
            resp.getWriter().write(dateOfCompletionList.toString());
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}