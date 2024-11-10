package by.audit.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {

    /**
     * Метод срабаьывает перед вызовом методов из указанного пакета и после,
     * замеряет вермя выполнения и выводит сообщение в консолью.
     * @param joinPoint
     * @return proceed.
     * @throws Throwable
     */
    @Around("@annotation(by.audit.annotation.Loggable)")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println("Время выполнения метода " + joinPoint.getSignature() + " составило " + executionTime + " мс");
        return proceed;
    }
}