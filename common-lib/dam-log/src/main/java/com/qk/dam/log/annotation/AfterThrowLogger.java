package com.qk.dam.log.annotation;

import com.qk.dam.log.enums.LogLevel;

import java.lang.annotation.*;

/**
 * @author shenpengjie
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AfterThrowLogger {

    /**
     * 日志级别
     */
    LogLevel logLevel() default LogLevel.INFO;
}
