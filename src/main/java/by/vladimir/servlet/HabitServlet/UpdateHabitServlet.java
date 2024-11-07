package by.vladimir.servlet.HabitServlet;

import by.vladimir.annotation.Audit;
import by.vladimir.dto.HabitDto;
import by.vladimir.service.HabitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Audit
@WebServlet("/updateHabit")
public class UpdateHabitServlet extends HttpServlet {

    private final HabitService habitService = HabitService.getInstance();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HabitDto habitDto = objectMapper.readValue(req.getReader(), HabitDto.class);
        try {
            habitService.update(habitDto);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}