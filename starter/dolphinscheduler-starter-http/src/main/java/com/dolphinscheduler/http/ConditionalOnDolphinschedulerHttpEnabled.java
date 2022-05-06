package com.dolphinscheduler.http;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhudaoming
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@ConditionalOnProperty(name = "dolphinscheduler.task.taskType",havingValue = "HTTP")
public @interface ConditionalOnDolphinschedulerHttpEnabled {
}