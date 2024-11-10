package by.vladimir.controller;

import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.service.implementation.HabitServiceImpl;

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

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class HabitControllerTest {


    private MockMvc mockMvc;

    private HabitController habitController;

    @MockBean
    private HabitServiceImpl habitServiceImpl;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        habitController = new HabitController(habitServiceImpl);
        mockMvc = MockMvcBuilders.standaloneSetup(habitController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddHabit() throws Exception {

        CreateHabitDto createHabitDto = new CreateHabitDto("Running", "Exercise daily","DAILY",1L);
        Habit mockHabit = new Habit(10L,"Running", "Exercise daily",Frequency.DAILY,1L);
        Mockito.when(habitServiceImpl.create(createHabitDto)).thenReturn(mockHabit);

        mockMvc.perform(MockMvcRequestBuilders.post("/habit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createHabitDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Running"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Exercise daily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.frequency").value("DAILY"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("1"));

    }
    @Test
    public void testUpdateHabit() throws Exception {

        HabitDto habitDto = new HabitDto(10L,"Gym","qweqew",Frequency.DAILY);
        Habit mockHabit2 = new Habit(10L,"Gym","qweqew",Frequency.DAILY,1L);
        Mockito.when(habitServiceImpl.containById(10L)).thenReturn(true);
        Mockito.when(habitServiceImpl.update(habitDto)).thenReturn(mockHabit2);

        mockMvc.perform(MockMvcRequestBuilders.put("/habit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(habitDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Gym"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("qweqew"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.frequency").value("DAILY"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("1"));
    }

    @Test
    public void testDeleteHabit() throws Exception {
        Long habitId = 1L;
        Mockito.when(habitServiceImpl.containById(habitId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/habit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(habitId))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllUserHabit() throws Exception {
        User user = new User(1L,"johndoe@example.com","qwerty",Role.USER);
        List<Habit> mockHabitList = new ArrayList<>();
        mockHabitList.add(new Habit(10L,"Running", "Exercise daily", Frequency.DAILY,1L));
        mockHabitList.add(new Habit(11L,"Reading", "Read" ,Frequency.DAILY,1L));

        Mockito.when(habitServiceImpl.getAll(user)).thenReturn(mockHabitList);

        mockMvc.perform(MockMvcRequestBuilders.get("/habit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Running"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Exercise daily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Reading"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Read"));
    }

}
