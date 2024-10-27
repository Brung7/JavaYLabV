package by.vladimir.servlet.DateOfCompletionServlet;

import by.vladimir.annotation.Audit;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.service.DateOfCompletionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Audit
@WebServlet("/deleteDate")
public class DeleteDateOfCompletionServlet extends HttpServlet {

    DateOfCompletionService dateOfCompletionService = DateOfCompletionService.getInstance();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DateOfCompletion dateOfCompletion = objectMapper.readValue(req.getReader(),DateOfCompletion.class);

        try {
            dateOfCompletionService.delete(dateOfCompletion.getId());
            resp.getWriter().write("Date delete successfully");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("Delete date failed");
        }
    }
}