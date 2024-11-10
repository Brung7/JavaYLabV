package by.vladimir.service.implementation;

import by.audit.annotation.AuditAspect;
import by.audit.annotation.Loggable;
import by.vladimir.dao.HabitDao;
import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Frequency;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.mapper.HabitMapper;
import by.vladimir.service.HabitService;
import by.vladimir.validator.CreateHabitValidator;
import by.vladimir.validator.UpdateHabitValidator;
import by.vladimir.validator.ValidationResult;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Класс service ответственный за работу с привычками.
 */
@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitDao habitDao;

    private final HabitMapper habitMapper;

    private final CreateHabitValidator habitValidator;

    private final UpdateHabitValidator updateHabitValidator;

    @AuditAspect
    @Loggable
    @Override
    public Habit getHabitById(Long id){
        Optional<Habit> habitOptional = habitDao.findById(id);
        if(habitOptional.isPresent()){
            return habitOptional.get();
        }
        else {
            return null;
        }
    }

    @AuditAspect
    @Loggable
    @Override
    public List<Habit> getAll(User user) {
        Long id = user.getId();
        Optional<List<Habit>> optionalHabits = Optional.ofNullable(habitDao.findByUserId(id));
        if (optionalHabits.isPresent()) {
            if (!habitDao.findByUserId(id).isEmpty()) {
                return habitDao.findByUserId(id);
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
    public Habit create(CreateHabitDto createHabitDto) {
        ValidationResult validationResult = habitValidator.isValid(createHabitDto);
        if(!validationResult.isValid()){
            throw new IllegalArgumentException("Invalid CreateHabitDto");
        }
        else {
            Habit habit = habitMapper.toHabit(createHabitDto);
            return habitDao.save(habit);
        }
    }

    @AuditAspect
    @Loggable
    @Override
    public Habit update(HabitDto habitDto) {
        ValidationResult validationResult = updateHabitValidator.isValid(habitDto);
        if(!validationResult.isValid()){
            throw new IllegalArgumentException("Invalid habitDto");
        }
        else {
            Habit habit = habitMapper.toHabitFromHabitDto(habitDto);
            habitDao.update(habit);
            return habit;
        }
    }

    @AuditAspect
    @Loggable
    @Override
    public boolean containById(Long id) {
        Optional<Habit> habitOptional = habitDao.findById(id);
        if (habitOptional.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @AuditAspect
    @Loggable
    @Override
    public void delete(Long id) {
        habitDao.delete(id);
    }

}