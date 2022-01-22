package com.qk.dam.log.aspect;

import com.qk.dam.log.annotation.PrintLog;
import com.qk.dam.log.enums.LogLevel;
import net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author shenpj
 * @date 2022/1/19 4:13 下午
 * @since 1.0.0
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    public static final String PRINT_ARGS = "参数【{}】值为【{}】";
    public static final String PRINT_RETURN = "方法【{}】返回值为【{}】";
    public static final String PRINT_START = "方法【{}】开始执行";
    public static final String PRINT_ENT = "方法【{}】执行结束，方法耗时【{}ms】";

    @Pointcut("execution(* com.qk..*.service..*.*(..))")
    public void serviceAspect() {
    }

    @Pointcut("execution(* com.qk..*.rest..*.*(..))")
    public void restAspect() {
    }

    @Pointcut("execution(* com.qk..*.controller..*.*(..))")
    public void controllerAspect() {
    }

    @Pointcut("@annotation(com.qk.dam.log.annotation.PrintLog)")
    public void annotationAspect() {
    }

    @Pointcut("annotationAspect()")
    public void finalAspect() {
    }


    /**
     * 环绕通知
     */
    @Around("@annotation(printLog)")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint, PrintLog printLog) throws Throwable {
        //方法名称
        String name = proceedingJoinPoint.getSignature().getName();
        //打印日志级别
        LogLevel logLevel = printLog.logLevel();
        //方法开始时打印
        logOutPut(logLevel, name, null, PRINT_START);
        //打印参数
        printArgs(proceedingJoinPoint, printLog);
        //开始时间
        long startTime = System.currentTimeMillis();
        //todo 利用反射调用目标方法，就是method.invoke()
        Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long entTime =System.currentTimeMillis() - startTime;
        //打印结果值
        printReturn(proceedingJoinPoint, printLog, result);
        //共耗时
        logOutPut(logLevel, name, entTime, PRINT_ENT);
        return result;
    }

    /**
     * 前置通知打印参数
     *
     * @param proceedingJoinPoint
     * @param printLog
     */
    private void printArgs(ProceedingJoinPoint proceedingJoinPoint, PrintLog printLog) {
        //需要打印的参数
        int[] printArgs = printLog.printArgs();
        //前置通知
        if (ArrayUtils.isNotEmpty(printArgs)) {
            //打印日志级别
            LogLevel logLevel = printLog.logLevel();
            //参数名称
            String[] paramNames = ((CodeSignature) proceedingJoinPoint.getSignature()).getParameterNames();
            //参数结果
            Object[] args = proceedingJoinPoint.getArgs();
            Arrays.stream(printArgs).forEach(i -> {
                if (i < args.length && i < paramNames.length) {
                    logOutPut(logLevel, paramNames[i], args[i], PRINT_ARGS);
                }
            });
        }
    }

    /**
     * 后置返回值打印
     *
     * @param proceedingJoinPoint
     * @param printLog
     * @param result
     */
    private void printReturn(ProceedingJoinPoint proceedingJoinPoint, PrintLog printLog, Object result) {
        //是否需要打印返回结果
        if (printLog.printReturn()) {
            //方法名称
            String name = proceedingJoinPoint.getSignature().getName();
            logOutPut(printLog.logLevel(), name, result, PRINT_RETURN);
        }
    }

    /**
     * 日志打印
     *
     * @param logLevel
     * @param param
     * @param value
     * @param output
     */
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

    /**
     * 获取方法中声明的注解
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    public PrintLog getDeclaredAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        PrintLog annotation = objMethod.getDeclaredAnnotation(PrintLog.class);
        // 返回
        return annotation;
    }
}
