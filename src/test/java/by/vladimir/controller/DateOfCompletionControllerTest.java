package by.vladimir.controller;

import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.service.DateOfCompletionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DateOfCompletionControllerTest {

    private MockMvc mockMvc;
    private DateOfCompletionController dateOfCompletionController;

    @Mock
    private DateOfCompletionService dateOfCompletionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dateOfCompletionController = new DateOfCompletionController(dateOfCompletionService);
        mockMvc = MockMvcBuilders.standaloneSetup(dateOfCompletionController).build();
    }

    @Test
    public void testCreateDateOfCompletion() throws Exception {
        CreateDateOfComplDto createDateOfComplDto = new CreateDateOfComplDto(10L,3L,"2024-11-02");

        mockMvc.perform(post("/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createDateOfComplDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateDateOfCompletion() throws Exception {
        DateOfCompletionDto dateOfCompletionDto = new DateOfCompletionDto(10L,"2024-11-03");

        mockMvc.perform(put("/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dateOfCompletionDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteDateOfCompletion() throws Exception {
        Long id = 1L;
        when(dateOfCompletionService.containById(id)).thenReturn(true);

        mockMvc.perform(delete("/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(id)))
                .andExpect(status().isOk())
                .andExpect(content().string("Дата удалена"));
    }

    @Test
    public void testGetAllHabitDate() throws Exception {
        Long habitId = 1L;

        mockMvc.perform(get("/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(habitId)))
                .andExpect(status().isOk());
    }
}
