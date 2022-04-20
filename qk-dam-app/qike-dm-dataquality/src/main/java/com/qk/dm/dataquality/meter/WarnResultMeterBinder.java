package com.qk.dm.dataquality.meter;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhudaoming
 */
public class WarnResultMeterBinder implements MeterBinder {
    private static final String NAME_RULE = "dqc.rule";
    public AtomicReference<BigDecimal> bigDecimalAtomicReference = new AtomicReference<>(BigDecimal.ZERO);
    public AtomicReference<String> st =  new AtomicReference<>("");

    @Override
    public void bindTo(MeterRegistry registry) {
        BigDecimal bigDecimal = bigDecimalAtomicReference.get();
        Gauge.builder(metricName("task.warn.result"), bigDecimal::doubleValue)
                .description("检查任务实例")
                .tag("state", "WARING")
                .tag("id", st.get())
                .tag("type", "TABLE")
                .register(registry);
    }

    public AtomicReference<BigDecimal> getBigDecimalAtomicReference() {
        return bigDecimalAtomicReference;
    }

    public AtomicReference<String> getSt() {
        return st;
    }

    private static String metricName(String name) {
        return String.join(".", NAME_RULE, name);
    }
}
