package com.qk.dm.dataquality.meter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author zhudaoming
 */
@Aspect
@Component
@Slf4j
public class RuleMeterAspect {
    @Pointcut("within(com.qk.dm.dataquality.service..*)")
    public void inServiceLayer() {}

    @Pointcut("@annotation(com.qk.dm.dataquality.meter.Meter)")
    public void annotationMeter() {}

    @Pointcut("inServiceLayer()&&annotationMeter()")
    private void operation() {}

    @Around("operation()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        return retVal;
    }
}
