package com.qk.dm.dataquality.meter;

import java.lang.annotation.*;

/**
 * 埋点注解，用于统计Prometheus指标
 * @author zhudaoming
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Meter {

}
