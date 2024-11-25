package by.vladimir.service.implementation;

import by.audit.annotation.AuditAspect;
import by.audit.annotation.Loggable;
import by.vladimir.dao.DateOfCompletionDao;
import by.vladimir.dto.CreateDateOfComplDto;

import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.mapper.DateOfCompletionMapper;
import by.vladimir.service.DateOfCompletionService;
import by.vladimir.validator.CreateDateValidator;
import by.vladimir.validator.UpdateDateValidator;
import by.vladimir.validator.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Класс service ответственный за работу с датами выполнения.
 */
@Service
@RequiredArgsConstructor
public class DateOfCompletionServiceImpl implements DateOfCompletionService {

    private final DateOfCompletionDao dateDao;

    private final CreateDateValidator createDateValidator;

    private final DateOfCompletionMapper dateMapper;

    private final UpdateDateValidator updateDateValidator;


    @AuditAspect
    @Loggable
    @Override
    public DateOfCompletion create(CreateDateOfComplDto createDateOfComplDto) {
        ValidationResult validationResult = createDateValidator.isValid(createDateOfComplDto);
        if(!validationResult.isValid()){
            throw new IllegalArgumentException("Invalid CreateDateOfComplDto");
        }
        else {
            DateOfCompletion date = dateMapper.toDateOfCompl(createDateOfComplDto);
            return dateDao.save(date);
        }
    }


    @AuditAspect
    @Loggable
    @Override
    public void update(DateOfCompletionDto dateOfComplDto) {
        ValidationResult validationResult = updateDateValidator.isValid(dateOfComplDto);
        if(!validationResult.isValid()){
            throw new IllegalArgumentException("Invalid DateOfCompletionDto");
        }
        else {
            DateOfCompletion date = dateMapper.toDateOfComplFromDto(dateOfComplDto);
            dateDao.update(date);
        }
    }


    @AuditAspect
    @Loggable
    @Override
    public void delete(Long id) {
        dateDao.delete(id);
    }


    @AuditAspect
    @Loggable
    @Override
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

    @AuditAspect
    @Loggable
    @Override
    public boolean containById(Long id) {
        Optional<DateOfCompletion> date = dateDao.findById(id);
        return date.isPresent();
    }
}