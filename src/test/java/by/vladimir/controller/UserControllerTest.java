package by.vladimir.controller;

import by.vladimir.dto.CreateUserDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.dto.UserDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.filter.RoleFilter;
import by.vladimir.service.implementation.UserServiceImpl;
import by.vladimir.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private MockMvc mockMvc;

    private UserController userController;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private RoleFilter roleFilter;

    private ObjectMapper objectMapper;

    @Mock
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userServiceImpl,jwtUtils,roleFilter);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();


    }

    @Test
    public void testRegistration() throws Exception {

        CreateUserDto createUserDto = new CreateUserDto("example@mail.su", "qwerty", "USER");
        User mockUser = new User(4L,"example@mail.su", "qwerty", Role.USER);
        Mockito.when(userServiceImpl.registration(createUserDto)).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("example@mail.su"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("qwerty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("USER"));
    }

    @Test
    public void testLogin() throws Exception{
        CreateUserDto userDto = new CreateUserDto("example@mail.su", "qwerty", "USER");
        User mockUser = new User(4L,"example@mail.su", "qwerty", Role.USER);
        Mockito.when(userServiceImpl.authentication("example@mail.su")).thenReturn(Optional.of(mockUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetAllUsers() throws Exception {

        Mockito.when(roleFilter.isAdmin("ADMIN")).thenReturn(true);

        List<HabitDto> habitDtoList = new ArrayList<>();
        HabitDto habitDto = new HabitDto(10L,"Exercise","pypypypy",Frequency.WEEKLY);
        HabitDto habitDto2 = new HabitDto(11L,"Learning","asdasda", Frequency.DAILY);
        habitDtoList.add(habitDto);
        habitDtoList.add(habitDto2);

        List<UserDto> mockUserDtos = Arrays.asList(new UserDto(4L,"example@mail.su",habitDtoList));
        Mockito.when(userServiceImpl.getAllUsersWithHabits()).thenReturn(mockUserDtos);


        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .param("role", "ADMIN")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .param("role", "USER")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof RuntimeException));
    }
}

