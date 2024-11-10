package by.vladimir.dao;

import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.utils.DateFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;

@Testcontainers
@SpringBootTest
public class HabitDaoTest {

    @Autowired
    private HabitDao habitDao;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @BeforeEach
    public  void setUp(){
        postgreSQLContainer.start();
    }

    @AfterEach
    public  void tearDown() {

        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) throws SQLException {
        registry.add("database.url", postgreSQLContainer::getJdbcUrl);
        registry.add("database.username", postgreSQLContainer::getUsername);
        registry.add("database.password", postgreSQLContainer::getPassword);

    }

    @Test
    public void testSaveHabit(){
        Habit habit = new Habit();
        habit.setName("Gym");
        habit.setFrequency(Frequency.DAILY);
        habit.setDescription("Dqweqweq");
        habit.setUserId(1L);

        Habit savedHabit = habitDao.save(habit);

        assertThat(savedHabit).isNotNull();
        assertThat(savedHabit.getId()).isNotNull();
        assertThat(savedHabit.getName()).isEqualTo("Gym");
    }

    @Test
    public void testDeleteHabit(){
        Habit habit = new Habit();
        habit.setName("Gym");
        habit.setFrequency(Frequency.DAILY);
        habit.setDescription("Dqweqweq");
        habit.setUserId(1L);

        Habit savedHabit = habitDao.save(habit);
        habitDao.delete(savedHabit.getId());

        Optional<Habit> habitOptional = habitDao.findById(savedHabit.getId());
        assertThat(habitOptional).isEmpty();

    }

    @Test
    public void testUpdateHabit(){
        Habit habit = new Habit();
        habit.setName("Gym");
        habit.setFrequency(Frequency.DAILY);
        habit.setDescription("Dqweqweq");
        habit.setUserId(1L);
        Habit savedHabit = habitDao.save(habit);

        assertNotNull(savedHabit.getId());

        Habit habit2 = new Habit();
        habit.setName("Learning");
        habit.setFrequency(Frequency.WEEKLY);
        habit.setDescription("Qwerty");
        habit.setUserId(1L);

        habitDao.update(habit2);

        Habit updateHabit = habitDao.findById(savedHabit.getId()).orElseThrow();

        assertThat(updateHabit.getName()).isEqualTo("Learning");
        assertThat(updateHabit.getName()).isEqualTo(savedHabit.getName());

        habitDao.delete(updateHabit.getId());
    }

    @Test
    public void testFindById(){
        Habit habit = new Habit();
        habit.setName("Gym");
        habit.setFrequency(Frequency.DAILY);
        habit.setDescription("Dqweqweq");
        habit.setUserId(1L);
        Habit savedHabit = habitDao.save(habit);

        Optional<Habit> findHabit = habitDao.findById(savedHabit.getId());
        assertThat(findHabit.isPresent());
        assertThat(findHabit.get().getName()).isEqualTo("Gym");
    }
}