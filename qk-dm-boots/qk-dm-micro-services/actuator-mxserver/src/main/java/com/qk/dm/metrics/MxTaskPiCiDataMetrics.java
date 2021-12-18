// package com.qk.dm.metrics;
//
// import io.micrometer.core.instrument.Counter;
// import io.micrometer.core.instrument.Gauge;
// import io.micrometer.core.instrument.MeterRegistry;
// import io.micrometer.core.instrument.binder.MeterBinder;
// import org.springframework.stereotype.Component;
//
// import java.util.HashMap;
// import java.util.Map;
//
/// **
// * @author wjq
// * @date 2021/6/22 17:00
// * @since 1.0.0
// */
// @Component
// public class MxTaskPiCiDataMetrics implements MeterBinder {
//    public Map<String, Double> map;
//    public Gauge gauge;
//
//    MxTaskPiCiDataMetrics() {
//        map = new HashMap<>();
//    }
//
//    @Override
//    public void bindTo(MeterRegistry meterRegistry) {
//        this.gauge = Gauge.builder("task_pici_gauge", map, x -> x.get("tableName"))
//                .tags("tableName", String.valueOf(map.get("tableName")), "pici",
// String.valueOf(map.get("pici")))
//                .description("description-Job gauge")
//                .register(meterRegistry);
//    }
//
// }
