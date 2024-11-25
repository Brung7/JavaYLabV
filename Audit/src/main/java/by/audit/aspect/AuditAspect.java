package by.audit.aspect;

import by.audit.auditservice.impl.AuditServiceImpl;
import by.audit.dto.AuditDto;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Класс для работы с аспектами
 */
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditServiceImpl auditServiceImpl;

    /**
     * Метод сроабатывает после выполнения методов из пакета UserService, получает результат выполнения метода,
     * создает объект аудита и передает его в сервис.
     *
     * @param joinPoint
     */
    @Around("@annotation(by.audit.annotation.AuditAspect)")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = "User";
        String methodName = joinPoint.getSignature().getName();
        LocalDateTime timestamp = LocalDateTime.now();
        String actionResult = "";
        try{
            Object resultObj = joinPoint.proceed();
            actionResult = "успешно";
            return  resultObj;
        }
        catch (Exception e){
            actionResult = "неуспешно";
            throw e;
        }
        finally {
            AuditDto audit = new AuditDto(name, methodName, timestamp, actionResult);
            auditServiceImpl.saveAudit(audit);
        }

    }
}