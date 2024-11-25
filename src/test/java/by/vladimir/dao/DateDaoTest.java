package by.vladimir.dao;


import by.vladimir.entity.DateOfCompletion;
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
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Testcontainers
@SpringBootTest
public class DateDaoTest {

    @Autowired
    private DateOfCompletionDao dateOfCompletionDao;


    @Autowired
    private DateFormatter dateFormatter;

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
    public void testSaveDate(){
        DateOfCompletion date = new DateOfCompletion();
        date.setDate(new Date());
        date.setHabitId(1L);

        DateOfCompletion savedDate = dateOfCompletionDao.save(date);

        assertNotNull(savedDate.getId());
        assertEquals(date.getDate(), savedDate.getDate());
        assertEquals(date.getHabitId(), savedDate.getHabitId());
    }

    @Test
    public void testUpdateDate(){
        DateOfCompletion date = new DateOfCompletion();
        Date sDate = dateFormatter.formatterStrToDate("2024-10-10");
        Date uDate = dateFormatter.formatterStrToDate("2024-11-11");
        date.setDate(sDate);
        date.setHabitId(1L);
        DateOfCompletion savedDate = dateOfCompletionDao.save(date);

        assertNotNull(savedDate.getId());

        DateOfCompletion date2 = new DateOfCompletion();
        date.setDate(uDate);
        date.setHabitId(1L);

        dateOfCompletionDao.update(date2);

        DateOfCompletion updateDateOfCompletion = dateOfCompletionDao.findById(date2.getId()).orElseThrow();
        assertThat(updateDateOfCompletion.getDate()).isEqualTo("2024-11-11");
        dateOfCompletionDao.delete(updateDateOfCompletion.getId());
    }

    @Test
    public void testDeleteDate(){
        DateOfCompletion date = new DateOfCompletion();
        date.setDate(new Date());
        date.setHabitId(1L);

        DateOfCompletion savedDate = dateOfCompletionDao.save(date);
        dateOfCompletionDao.delete(savedDate.getId());

        Optional<DateOfCompletion> dateOfCompletion = dateOfCompletionDao.findById(savedDate.getId());
        assertThat(dateOfCompletion).isEmpty();
    }

    @Test
    public void testFindById(){
        DateOfCompletion date = new DateOfCompletion();
        date.setDate(new Date());
        date.setHabitId(1L);

        DateOfCompletion savedDate = dateOfCompletionDao.save(date);

        Optional<DateOfCompletion> findDateOfCompletion = dateOfCompletionDao.findById(savedDate.getId());

        assertThat(findDateOfCompletion.isPresent());
    }


}
