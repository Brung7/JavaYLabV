package by.vladimir.controller;


import by.vladimir.dto.CreateUserDto;
import by.vladimir.entity.Role;
import by.vladimir.entity.User;
import by.vladimir.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest {
    private MockMvc mockMvc;
    private UserController userController;
    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

    }

    @Test
    public void testRegistrationEndpoint() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto("username", "password","USER");

        User expectedUser = new User(1L,"username", "password", Role.USER);
        when(userService.registration(createUserDto)).thenReturn(expectedUser);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\":\"username\",\"password\":\"password\", \"role\":\"USER\"}"));
    }
}
