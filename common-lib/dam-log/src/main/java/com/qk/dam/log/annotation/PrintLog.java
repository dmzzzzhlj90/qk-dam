package com.qk.dam.log.annotation;

import com.qk.dam.log.enums.LogLevel;

import java.lang.annotation.*;

/**
 * @author shenpengjie
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrintLog {
    /**
     * 参数集 {0,1,2}
     */
    int[] printArgs() default {};

    /**
     * ture-打印返回值 false-不打印
     */
    boolean printReturn() default false;

    /**
     * 日志级别
     */
    LogLevel logLevel() default LogLevel.INFO;
}
