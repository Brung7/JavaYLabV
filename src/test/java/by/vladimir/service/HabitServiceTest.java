package by.vladimir.service;


import by.vladimir.dao.HabitDao;
import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.mapper.HabitMapper;
import by.vladimir.service.implementation.HabitServiceImpl;
import by.vladimir.validator.CreateHabitValidator;
import by.vladimir.validator.UpdateHabitValidator;
import by.vladimir.validator.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class HabitServiceTest {

    @InjectMocks
    private HabitServiceImpl habitService;

    @Mock
    private HabitDao habitDao;

    @Mock
    private CreateHabitValidator habitValidator;

    @Mock
    private HabitMapper habitMapper;

    @Mock
    private UpdateHabitValidator updateHabitValidator;

    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("database.url", postgresqlContainer::getJdbcUrl);
        registry.add("database.username", postgresqlContainer::getUsername);
        registry.add("database.password", postgresqlContainer::getPassword);
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        postgresqlContainer.start();
    }

    @Test
    public void testGetAll() {

        User user = new User();
        user.setId(6L);
        Habit habit1 = new Habit();
        habit1.setName("Habit 1");
        Habit habit2 = new Habit();
        habit2.setName("Habit 2");

        when(habitDao.findByUserId(user.getId())).thenReturn(List.of(habit1, habit2));

        List<Habit> habits = habitService.getAll(user);

        assertNotNull(habits);
        assertEquals(2, habits.size());
        assertEquals("Habit 1", habits.get(0).getName());
        assertEquals("Habit 2", habits.get(1).getName());
    }

    @Test
    public void testGetAll_NoHabits_ReturnsEmptyList() {

        User user = new User();
        user.setId(6L);
        when(habitDao.findByUserId(user.getId())).thenReturn(Collections.emptyList());

        List<Habit> habits = habitService.getAll(user);

        assertNotNull(habits);
        assertTrue(habits.isEmpty());
    }

    @Test
    public void testCreate() {

        CreateHabitDto createHabitDto = new CreateHabitDto();
        createHabitDto.setName("New Habit");

        Habit habit = new Habit();
        habit.setName("New Habit");

        when(habitValidator.isValid(createHabitDto)).thenReturn(new ValidationResult());
        when(habitMapper.toHabit(createHabitDto)).thenReturn(habit);
        when(habitDao.save(habit)).thenReturn(habit);


        Habit createdHabit = habitService.create(createHabitDto);

        assertNotNull(createdHabit);
        assertEquals("New Habit", createdHabit.getName());
    }


    @Test
    public void testUpdate() {

        HabitDto habitDto = new HabitDto();
        habitDto.setName("Updated Habit");

        Habit habit = new Habit();
        habit.setName("Updated Habit");

        when(updateHabitValidator.isValid(habitDto)).thenReturn(new ValidationResult());
        when(habitMapper.toHabitFromHabitDto(habitDto)).thenReturn(habit);

        Habit updatedHabit = habitService.update(habitDto);

        assertNotNull(updatedHabit);
        assertEquals("Updated Habit", updatedHabit.getName());

        verify(habitDao).update(habit);
    }

}