package by.vladimir.controller;

import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.service.implementation.DateOfCompletionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class DateOfCompletionControllerTest {

    private MockMvc mockMvc;

    private DateOfCompletionController dateOfCompletionController;

    @MockBean
    private DateOfCompletionServiceImpl dateOfCompletionServiceImpl;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dateOfCompletionController = new DateOfCompletionController(dateOfCompletionServiceImpl);
        mockMvc = MockMvcBuilders.standaloneSetup(dateOfCompletionController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateDateOfCompletion() throws Exception {
        CreateDateOfComplDto createDateOfComplDto = new CreateDateOfComplDto(10L,3L,"2024-11-02");

        mockMvc.perform(MockMvcRequestBuilders.post("/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDateOfComplDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateDateOfCompletion() throws Exception {
        DateOfCompletionDto dateOfCompletionDto = new DateOfCompletionDto(10L,"2024-11-03");

        mockMvc.perform(MockMvcRequestBuilders.put("/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dateOfCompletionDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteDateOfCompletion() throws Exception {
        Long id = 1L;
        Mockito.when(dateOfCompletionServiceImpl.containById(id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(id)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllHabitDate() throws Exception {
        Long habitId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(habitId)))
                .andExpect(status().isOk());
    }
}
