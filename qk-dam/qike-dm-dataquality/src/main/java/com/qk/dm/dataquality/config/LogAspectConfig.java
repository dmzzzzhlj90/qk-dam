package com.qk.dm.dataquality.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
//@Component
@Slf4j
public class LogAspectConfig {

    /**
     * 环绕通知，打印超时方法日志
     * 3s error
     * 2s warn
     * <2s info
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.qk.dm.dataquality.handler.*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("===== 开始执行{}.{} =====",joinPoint.getTarget(),joinPoint.getSignature().getName());
        //开始时间
        long startTime = System.currentTimeMillis();
        //执行连接点方法
        Object proceed = joinPoint.proceed();
        //总耗时
        long duration = System.currentTimeMillis() - startTime;
        if (duration > 3000) {
            log.error("===== 执行结束，耗时：{} ms =====", duration);
        } else if (duration > 2000) {
            log.warn("===== 执行结束，耗时：{} ms =====", duration);
        } else {
            log.info("===== 执行结束，耗时：{} ms =====", duration);
        }
        return proceed;
    }


}
