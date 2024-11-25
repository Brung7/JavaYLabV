package by.vladimir.service;

import by.vladimir.dao.DateOfCompletionDao;
import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.mapper.DateOfCompletionMapper;
import by.vladimir.service.implementation.DateOfCompletionServiceImpl;
import by.vladimir.utils.DateFormatter;
import by.vladimir.validator.CreateDateValidator;
import by.vladimir.validator.UpdateDateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
public class DateOfCompletionServiceTest {

    @InjectMocks
    private DateOfCompletionServiceImpl dateOfCompletionService;

    @Mock
    private DateOfCompletionDao dateDao;

    @Mock
    private CreateDateValidator createDateValidator;

    @Mock
    private DateOfCompletionMapper dateMapper;

    @Mock
    private UpdateDateValidator updateDateValidator;

    @Mock
    private DateFormatter dateFormatter;

    static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest");


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
    public void testCreate_ValidDto_CreatesDateOfCompletion() {
        CreateDateOfComplDto createDto = new CreateDateOfComplDto();
        createDto.setId(10L);
        createDto.setDate("2024-11-10");
        createDto.setHabitId(1L);

        DateOfCompletion date = new DateOfCompletion();
        date.setId(11L);
        date.setDate(dateFormatter.formatterStrToDate("2024-10-10"));
        date.setHabitId(1L);

        when(createDateValidator.isValid(createDto)).thenReturn(createDateValidator.isValid(createDto));
        when(dateMapper.toDateOfCompl(createDto)).thenReturn(date);
        when(dateDao.save(date)).thenReturn(date);

        DateOfCompletion createdDate = dateOfCompletionService.create(createDto);

        assertNotNull(createdDate);
    }

    @Test
    public void testCreate_InvalidDto_ThrowsException() {
        CreateDateOfComplDto createDto = new CreateDateOfComplDto();

        when(createDateValidator.isValid(createDto)).thenAnswer(invocation -> false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dateOfCompletionService.create(createDto);
        });
        assertEquals("Invalid CreateDateOfComplDto", exception.getMessage());
    }

    @Test
    public void testUpdate_ValidDto_UpdatesDateOfCompletion() {
        Date dateStr = dateFormatter.formatterStrToDate("2024-10-10");
        DateOfCompletionDto updateDto = new DateOfCompletionDto();
        updateDto.setId(15L);
        updateDto.setDate("2024-10-11");

        DateOfCompletion date = new DateOfCompletion();
        date.setId(13L);
        date.setDate(dateStr);
        date.setHabitId(1L);
        when(updateDateValidator.isValid(updateDto)).thenAnswer(invocation -> true);
        when(dateMapper.toDateOfComplFromDto(updateDto)).thenReturn(date);

        dateOfCompletionService.update(updateDto);

        verify(dateDao).update(date);
    }

    @Test
    public void testUpdate_InvalidDto_ThrowsException() {

        DateOfCompletionDto updateDto = new DateOfCompletionDto();

        when(updateDateValidator.isValid(updateDto)).thenAnswer(invocation -> true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dateOfCompletionService.update(updateDto);
        });

        assertEquals("Invalid DateOfCompletionDto", exception.getMessage());
    }

    @Test
    public void testDelete() {

        Long id = 1L;

        dateOfCompletionService.delete(id);

        verify(dateDao).delete(id);
    }

    @Test
    public void testFindByHabitId() {

        Long habitId = 1L;
        DateOfCompletion date1 = new DateOfCompletion();
        DateOfCompletion date2 = new DateOfCompletion();

        when(dateDao.findByHabitId(habitId)).thenReturn(List.of(date1, date2));

        List<DateOfCompletion> result = dateOfCompletionService.findByHabitId(habitId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByHabitId_NoDates_ReturnsEmptyList() {

        Long habitId = 1L;

        when(dateDao.findByHabitId(habitId)).thenReturn(Collections.emptyList());

        List<DateOfCompletion> result = dateOfCompletionService.findByHabitId(habitId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testContainById_DateExists_ReturnsTrue() {

        Long id = 1L;
        DateOfCompletion date = new DateOfCompletion();

        when(dateDao.findById(id)).thenReturn(Optional.of(date));

        boolean exists = dateOfCompletionService.containById(id);

        assertTrue(exists);
    }

    @Test
    public void testContainById_DateDoesNotExist_ReturnsFalse() {

        Long id = 1L;

        when(dateDao.findById(id)).thenReturn(Optional.empty());

        boolean exists = dateOfCompletionService.containById(id);

        assertFalse(exists);
    }
}
