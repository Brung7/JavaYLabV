package by.vladimir.service;

import by.vladimir.dao.HabitDao;
import by.vladimir.dto.CreateHabitDto;
import by.vladimir.dto.HabitDto;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.mapper.HabitMapper;
import by.vladimir.mapper.HabitUpdateMapper;

import java.util.*;

public class HabitService {
    private static final HabitService INSTANCE = new HabitService();
    private final HabitDao habitDao = HabitDao.getInstance();
    private final HabitMapper habitMapper = HabitMapper.getInstance();
    private final HabitUpdateMapper habitUpdateMapper = HabitUpdateMapper.getINSTANCE();

    public List<Habit> getUserHabits(User user) {
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

    public Habit createHabit(CreateHabitDto createHabitDto) {
        Habit habit = habitMapper.mapFrom(createHabitDto);
        return habitDao.save(habit);
    }

    public void update(HabitDto habitDto) {
        Habit habit = habitUpdateMapper.mapFrom(habitDto);
        habitDao.update(habit);
    }

    public boolean containById(Long id) {
        Optional<Habit> habitOptional = habitDao.findById(id);
        if (habitOptional.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(Long id) {
        habitDao.delete(id);
    }


    private HabitService() {
    }

    public static HabitService getInstance() {
        return INSTANCE;
    }

}
