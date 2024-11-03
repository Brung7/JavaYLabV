package by.vladimir.aspect;

import by.vladimir.dto.AuditDto;
import by.vladimir.entity.DateOfCompletion;
import by.vladimir.entity.Habit;
import by.vladimir.entity.User;
import by.vladimir.service.AuditService;
import by.vladimir.service.HabitService;
import by.vladimir.service.UserService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;

import java.time.LocalDateTime;

/**
 * Класс для работы с аспектами
 */
@Aspect
@Component
public class AuditAspect {

    private AuditService auditService;

    private UserService userService;

    private HabitService habitService;

    public AuditAspect(AuditService auditService, UserService userService, HabitService habitService) {
        this.auditService = auditService;
        this.userService = userService;
        this.habitService = habitService;
    }

    /**
     * Метод сроабатывает после выполнения методов из пакета UserService, получает результат выполнения метода,
     * создает объект аудита и передает его в сервис.
     * @param joinPoint
     * @param result - результат выполениня метода из пакета.
     */
    @AfterReturning(pointcut = "execution(* by.vladimir.service.UserService.*(..))", returning = "result")
    public void afterReturningUser(JoinPoint joinPoint, User result) {
        String name = result.getEmail();
        String methodName = joinPoint.getSignature().getName();
        LocalDateTime timestamp = LocalDateTime.now();
        String actionResult = (result != null) ? "успешно" : "неуспешно";
        AuditDto audit = new AuditDto(name, methodName, timestamp, actionResult);
        auditService.saveAudit(audit);
    }

    /**
     * Метод сроабатывает после выполнения методов из пакета HabitService, получает результат выполнения метода,
     * создает объект аудита и передает его в сервис.
     * @param joinPoint
     * @param result - результат выполениня метода из пакета.
     */
    @AfterReturning(pointcut = "execution(* by.vladimir.service.HabitService.*(..))", returning = "result")
    public void afterReturningHabit(JoinPoint joinPoint, Habit result) {
        Long id = result.getUserId();
        User user = userService.findById(id);
        String name = user.getEmail();
        String methodName = joinPoint.getSignature().getName();
        LocalDateTime timestamp = LocalDateTime.now();
        String actionResult = (result != null) ? "успешно" : "неуспешно";
        AuditDto audit = new AuditDto(name, methodName, timestamp, actionResult);
        auditService.saveAudit(audit);
    }

    /**
     * Метод сроабатывает после выполнения методов из пакета DateOfCompletionService, получает результат выполнения метода,
     * создает объект аудита и передает его в сервис.
     * @param joinPoint
     * @param result - результат выполениня метода из пакета.
     */
    @AfterReturning(pointcut = "execution(* by.vladimir.service.DateOfCompletionService.*(..))", returning = "result")
    public void afterReturningDate(JoinPoint joinPoint, DateOfCompletion result) {
        Long habitId = result.getHabitId();
        Habit habit = habitService.getHabitById(habitId);
        Long id = habit.getUserId();
        User user = userService.findById(id);
        String name = user.getEmail();
        String methodName = joinPoint.getSignature().getName();
        LocalDateTime timestamp = LocalDateTime.now();
        String actionResult = (result != null) ? "успешно" : "неуспешно";
        AuditDto audit = new AuditDto(name, methodName, timestamp, actionResult);
        auditService.saveAudit(audit);
    }
}