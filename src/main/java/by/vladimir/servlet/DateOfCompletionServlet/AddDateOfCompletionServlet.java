package by.vladimir.servlet.DateOfCompletionServlet;

import by.vladimir.annotation.Audit;
import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.CreateHabitDto;
import by.vladimir.service.DateOfCompletionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Audit
@WebServlet("/newDate")
public class AddDateOfCompletionServlet extends HttpServlet {

    DateOfCompletionService dateOfCompletionService = DateOfCompletionService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateDateOfComplDto dateOfComplDto = objectMapper.readValue(req.getReader(), CreateDateOfComplDto.class);

        try {
            dateOfCompletionService.createDateOfCompletion(dateOfComplDto);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}