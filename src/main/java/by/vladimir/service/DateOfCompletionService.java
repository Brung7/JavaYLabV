package by.vladimir.service;

import by.vladimir.dao.DateOfCompletionDao;
import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.mapper.DateOfComplMapper;
import by.vladimir.mapper.DateOfCompletionUpdateMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DateOfCompletionService {
    private static final DateOfCompletionService INSTANCE = new DateOfCompletionService();
    private static final DateOfComplMapper dateMapper = DateOfComplMapper.getInstance();
    private static final DateOfCompletionDao dateDao = DateOfCompletionDao.getInstance();
    private static final DateOfCompletionUpdateMapper dateUpdateMapper = DateOfCompletionUpdateMapper.getInstance();

    private DateOfCompletionService() {
    }

    public static DateOfCompletionService getInstance() {
        return INSTANCE;
    }

    private static final DateOfCompletionDao dateOfCompletion = DateOfCompletionDao.getInstance();

    public DateOfCompletion createDateOfCompletion(CreateDateOfComplDto createDateOfComplDto) {
        DateOfCompletion date = dateMapper.mapFrom(createDateOfComplDto);
        return dateDao.save(date);
    }

    public void update(DateOfCompletionDto dateOfComplDto) {
        DateOfCompletion date = dateUpdateMapper.mapFrom(dateOfComplDto);
        dateDao.update(date);
    }

    public void delete(Long id) {
        dateDao.delete(id);
    }

    public List<DateOfCompletion> findByHabitId(Long habitId) {
        List<DateOfCompletion> dateOfCompletionList = dateDao.findByHabitId(habitId);
        Optional<List<DateOfCompletion>> dateOfCompletionOptional = Optional.ofNullable(dateOfCompletionList);
        if (dateOfCompletionOptional.isPresent()) {
            if (!dateOfCompletionList.isEmpty()) {
                return dateOfCompletionList;
            } else {
                return Collections.emptyList();
            }

        } else {
            return new ArrayList<>();
        }
    }

    public boolean containById(Long id) {
        Optional<DateOfCompletion> date = dateDao.findById(id);
        return date.isPresent();
    }
}
