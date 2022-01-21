package com.qk.dam.log.aspect;

import com.qk.dam.log.annotation.AfterReturnLogger;
import com.qk.dam.log.annotation.BeforeLogger;
import com.qk.dam.log.enums.LogLevel;
import com.qk.dam.log.utils.ObjectRestulUtil;
import com.qk.dam.log.utils.ParameterUtil;
import net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author shenpj
 * @date 2022/1/19 4:13 下午
 * @since 1.0.0
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    public static final String OUTPUT = "============【参数名称】：{}，【value值】：{}";
    public static final String RETURNOUTPUT = "============【方法名称】：{}，【返回值】：{}";

    @Pointcut("execution(* com.qk..*.service..*.*(..))")
    public void controllerAspect() {}

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before("controllerAspect()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //获取方法对象
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(BeforeLogger.class)) {
            log.info("================前置通知方法 {} 开始================", signature);
            //获取方法上注解
            BeforeLogger beforeLogger = targetMethod.getAnnotation(BeforeLogger.class);
            //日志级别
            LogLevel logLevel = beforeLogger.logLevel();
            //结果集解析
            int[] parameters = beforeLogger.parameters();
            if (ArrayUtils.isNotEmpty(parameters)) {
                Map<String, Object> nameAndValueMap = ParameterUtil.invokeParamters(joinPoint, parameters);
                for (Map.Entry<String, Object> entry : nameAndValueMap.entrySet()) {
                    logOutPut(logLevel, entry.getKey(), entry.getValue(), OUTPUT);
                }
            }
            //单个指定
            String lockParam = beforeLogger.param();
            if (StringUtils.isNotBlank(lockParam)) {
                // 解析EL表达式
                String evalAsText = ParameterUtil.evalLockParam(joinPoint, lockParam);
                logOutPut(logLevel, lockParam, evalAsText, OUTPUT);
            }
            log.info("================前置通知方法 {} 结束================", signature);
        }
    }


    /**
     * 后置返回通知
     *
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "controllerAspect()", returning = "result")
    public static void afterEnd(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //获取方法对象
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(AfterReturnLogger.class)) {
            log.info("================后置返回通知方法 {} 开始================", signature);
            //获取方法上注解
            AfterReturnLogger beforeLogger = targetMethod.getAnnotation(AfterReturnLogger.class);
            //日志级别
            LogLevel logLevel = beforeLogger.logLevel();
            //结果集解析
            String methodName = joinPoint.getSignature().getName();
            if (beforeLogger.returnResult()) {
                //todo 返回值名称需要获取
                logOutPut(logLevel, methodName, result, RETURNOUTPUT);
            }
            //单个指定
            String lockParam = beforeLogger.param();
            if (StringUtils.isNotBlank(lockParam)) {
                //自定义解析返回值
                Object evalAsText = ObjectRestulUtil.evalLockReturn(result, lockParam);
                logOutPut(logLevel, lockParam, evalAsText, OUTPUT);
            }
            log.info("================后置返回通知方法 {} 结束================", signature);
        }
    }


//    // 后置通知
//    @After("controllerAspect()")
//    public static void after(JoinPoint joinPoint) {
//        System.out.println("方法后");
//    }
//
//    // 后置异常通知
//    @AfterThrowing(value = "controllerAspect()",throwing="ex")
//    public static void afterException(JoinPoint joinPoint,Exception ex) {
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        //获取方法对象
//        Method targetMethod = methodSignature.getMethod();
//        if (targetMethod.isAnnotationPresent(AfterThrowLogger.class)) {
//            log.info("================后置异常通知方法 {} 开始================", signature);
//            //获取方法上注解
//            AfterThrowLogger beforeLogger = targetMethod.getAnnotation(AfterThrowLogger.class);
//            //日志级别
//            LogLevel logLevel = beforeLogger.logLevel();
//
//            log.info("===============异常报告: {} ================",ex);
//
//            log.info("================后置异常通知方法 {} 结束================", signature);
//        }
//    }
//
//    //环绕通知
//    @Around("@annotation(beforeLogger)")
//    public Object myAround(ProceedingJoinPoint proceedingJoinPoint, BeforeLogger beforeLogge) {
//        Object[] args = proceedingJoinPoint.getArgs();
//        String name = proceedingJoinPoint.getSignature().getName();
//        Object proceed = null;
//        try {
//            System.out.println("环绕前置通知:" + name + "方法开始，参数是" + Arrays.asList(args));
//            //利用反射调用目标方法，就是method.invoke()
//            proceed = proceedingJoinPoint.proceed(args);
//            System.out.println("环绕返回通知:" + name + "方法返回，返回值是" + proceed);
//        } catch (Throwable e) {
//            System.out.println("环绕异常通知" + name + "方法出现异常，异常信息是：" + e);
//        } finally {
//            System.out.println("环绕后置通知" + name + "方法结束");
//        }
//        return proceed;
//    }

    public static void logOutPut(LogLevel logLevel, String param, Object value, String output) {
        switch (logLevel) {
            case WARN:
                log.warn(output, param, value);
                break;
            case ERROR:
                log.error(output, param, value);
                break;
            case INFO:
                log.info(output, param, value);
                break;
            case DEBUG:
                log.debug(output, param, value);
                break;
            default:
                break;
        }
    }
}
