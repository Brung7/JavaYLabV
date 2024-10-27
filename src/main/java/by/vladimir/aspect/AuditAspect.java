package by.vladimir.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AuditAspect {

    @Pointcut("within(by.vladimir.annotation.Audit*)&& execution(* *(..))")
    public void annotatedByAudit() {}

    @Around("annotatedByAudit)")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("User action: " + joinPoint.getSignature().getName());

        return joinPoint.proceed();
    }
}