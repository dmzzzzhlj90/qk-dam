package com.qk.dm.dataquality.meter;

import com.qk.dm.dataquality.biz.TaskInstanceBiz;
import com.qk.dm.dataquality.biz.WarnBiz;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 监控数据质量规则指标
 * @author zhudaoming
 */
@Configuration(proxyBeanMethods = false)
public class RuleMeterConf {
    private static final String NAME_RULE = "dqc.rule";
    @Bean
    public MeterBinder ruleTemplateSize(final DqcRuleTemplateRepository dqcRuleTemplateRepository) {
        return (registry) ->
                Gauge.builder(metricName("template"), dqcRuleTemplateRepository::count)
                        .description("规则模板个数")
                        .tag("name","template")
                        .tag("rrr","xxx")
                        .baseUnit("个数")
                        .strongReference(true)
                        .register(registry);
    }

    @Bean
    public MeterBinder taskFieldInstance(final TaskInstanceBiz taskInstanceBiz) {
        return (registry) ->
        {
            Gauge.builder(metricName("task.instance"),taskInstanceBiz,
                            (t)->t.taskStateTypeStatistics(InstanceStateTypeEnum.SUCCESS.getCode()).getFieldCount())
                    .description("检查任务实例")
                    .tag("state","SUCCESS")
                    .tag("type","FIELD")
                    .register(registry);

            Gauge.builder(metricName("task.instance"),
                            taskInstanceBiz,
                            (t)->t.taskStateTypeStatistics(InstanceStateTypeEnum.FAILURE.getCode()).getFieldCount())
                    .description("检查任务实例")
                    .tag("state","FAILURE")
                    .tag("type","FIELD")
                    .register(registry);
            Gauge.builder(metricName("task.instance"),taskInstanceBiz,
                            (t)->t.taskStateTypeStatistics(InstanceStateTypeEnum.SUCCESS.getCode()).getTableCount())
                    .description("检查任务实例")
                    .tag("state","SUCCESS")
                    .tag("type","TABLE")
                    .register(registry);

            Gauge.builder(metricName("task.instance"),
                            taskInstanceBiz,
                            (t)->t.taskStateTypeStatistics(InstanceStateTypeEnum.FAILURE.getCode()).getTableCount())
                    .description("检查任务实例")
                    .tag("state","FAILURE")
                    .tag("type","TABLE")
                    .register(registry);
        };
    }

    @Bean
    public MeterBinder waringInstance(final WarnBiz warnBiz) {
        return (registry) ->
        {
            Gauge.builder(metricName("task.instance"),warnBiz,
                            (t)->t.warnStatistics().getFieldCount())
                    .description("检查任务实例")
                    .tag("state","WARING")
                    .tag("type","FIELD")
                    .register(registry);

            Gauge.builder(metricName("task.instance"),warnBiz,
                            (t)->t.warnStatistics().getTableCount())
                    .description("检查任务实例")
                    .tag("state","WARING")
                    .tag("type","TABLE")
                    .register(registry);
        };
    }


    private static String metricName(String name) {
        return String.join(".", NAME_RULE, name);
    }

}
