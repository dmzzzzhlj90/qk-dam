package com.qk.dm.dataquality.meter;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.qk.dm.dataquality.biz.TaskInstanceBiz;
import com.qk.dm.dataquality.biz.WarnBiz;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.entity.DqcSchedulerResult;
import com.qk.dm.dataquality.entity.QDqcSchedulerResult;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 监控数据质量规则指标
 *
 * @author zhudaoming
 */
@Configuration(proxyBeanMethods = false)
public class RuleMeterConf {
    private static final String NAME_RULE = "dqc.rule";

    @Bean
    public MeterBinder ruleTemplateSize(final DqcRuleTemplateRepository dqcRuleTemplateRepository) {
        return (registry) ->
                Gauge.builder(metricName("template"), dqcRuleTemplateRepository, CrudRepository::count)
                        .description("规则模板个数")
                        .tag("name", "template")
                        .tag("rrr", "xxx")
                        .baseUnit("个数")
                        .strongReference(true)
                        .register(registry);
    }

    @Bean
    public MeterBinder taskFieldInstance(final TaskInstanceBiz taskInstanceBiz) {
        return (registry) ->
        {
            Gauge.builder(metricName("task.instance"), taskInstanceBiz,
                            (t) -> t.taskStateTypeStatistics(InstanceStateTypeEnum.SUCCESS).getFieldCount())
                    .description("检查任务实例")
                    .tag("state", "SUCCESS")
                    .tag("type", "FIELD")
                    .register(registry);

            Gauge.builder(metricName("task.instance"),
                            taskInstanceBiz,
                            (t) -> t.taskStateTypeStatistics(InstanceStateTypeEnum.FAILURE).getFieldCount())
                    .description("检查任务实例")
                    .tag("state", "FAILURE")
                    .tag("type", "FIELD")
                    .register(registry);
            Gauge.builder(metricName("task.instance"), taskInstanceBiz,
                            (t) -> t.taskStateTypeStatistics(InstanceStateTypeEnum.SUCCESS).getTableCount())
                    .description("检查任务实例")
                    .tag("state", "SUCCESS")
                    .tag("type", "TABLE")
                    .register(registry);

            Gauge.builder(metricName("task.instance"),
                            taskInstanceBiz,
                            (t) -> t.taskStateTypeStatistics(InstanceStateTypeEnum.FAILURE).getTableCount())
                    .description("检查任务实例")
                    .tag("state", "FAILURE")
                    .tag("type", "TABLE")
                    .register(registry);
        };
    }

    @Bean
    public MeterBinder waringInstance(final WarnBiz warnBiz) {
        return (registry) ->
        {
            Gauge.builder(metricName("task.instance"), warnBiz,
                            (t) -> t.warnStatistics().getFieldCount())
                    .description("检查任务实例")
                    .tag("state", "WARING")
                    .tag("type", "FIELD")
                    .register(registry);

            Gauge.builder(metricName("task.instance"), warnBiz,
                            (t) -> t.warnStatistics().getTableCount())
                    .description("检查任务实例")
                    .tag("state", "WARING")
                    .tag("type", "TABLE")
                    .register(registry);
        };
    }


    public static String metricName(String... names) {
        Joiner joiner = Joiner.on(",");
        ArrayList<String> ts = Lists.newArrayList(NAME_RULE);
        ts.addAll(List.of(names));
        return  joiner.join(ts);
    }

}
