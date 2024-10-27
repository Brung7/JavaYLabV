package by.vladimir.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggableAspect {

    @Pointcut("within(by.vladimir.annotation.Loggable*)&& execution(* *(..))")
    public void annotatedByLoggable(){
    }

    @Around("annotatedByLoggable")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Вызывается метод " + proceedingJoinPoint.getSignature());
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        System.out.println("Выполняется метод " + proceedingJoinPoint.getSignature() +
                " завершился. Время выполнения " + end + "ms");
        return result;
    }
}