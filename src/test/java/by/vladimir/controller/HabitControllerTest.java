package by.vladimir.controller;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.service.HabitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HabitControllerTest {

    private MockMvc mockMvc;
    private HabitController habitController;

    @Mock
    private HabitService habitService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        habitController = new HabitController(habitService);
        mockMvc = MockMvcBuilders.standaloneSetup(habitController).build();
    }

    @Test
    public void testAddHabit() throws Exception {
        CreateHabitDto createHabitDto = new CreateHabitDto("HabitName","Description","DAILY",3L);

        mockMvc.perform(post("/habit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createHabitDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateHabit() throws Exception {
        HabitDto habitDto = new HabitDto(5L,"UpdatedHabitName","Description", Frequency.DAILY);

        mockMvc.perform(put("/habit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(habitDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllUserHabit() throws Exception {
        User user = new User(10L,"username","password", Role.USER);
        List<Habit> habitList = new ArrayList<>();

        when(habitService.getUserHabits(user)).thenReturn(habitList);
        mockMvc.perform(get("/habit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());

    }

    @Test
    public void testDeleteHabit() throws Exception {
        Long id = 1L;
        when(habitService.containById(id)).thenReturn(true);

        mockMvc.perform(delete("/habit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(id)))
                .andExpect(status().isOk())
                .andExpect(content().string("Привычка удалена"));
    }
}
