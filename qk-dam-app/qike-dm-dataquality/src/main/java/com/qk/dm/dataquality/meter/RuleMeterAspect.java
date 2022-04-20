package com.qk.dm.dataquality.meter;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import com.qk.dm.dataquality.entity.QDqcSchedulerRules;
import com.qk.dm.dataquality.repositories.DqcSchedulerRulesRepository;
import com.qk.dm.dataquality.service.DqcSchedulerResultDataService;
import com.qk.dm.dataquality.vo.DqcSchedulerResultTitleVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultVO;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 质量监控prometheus指标获取
 *
 * @author zhudaoming
 */
@Aspect
@Component
@Slf4j
public class RuleMeterAspect {
    private static final String NAME_RULE = "dqc.rule";
    /** 规则id rule io*/
    public static final String RULEID = "RULEID";
    /** 规则结果标题*/
    public static final String TITLE = "TITLE";
    /** 规则结果数据索引*/
    public static final String DATAINDEX = "DATAINDEX";
    /** 规则结果检查对象*/
    public static final String TARGETOBJ = "TARGETOBJ";
    public static final String CACHE_NAME_DQC = "dqc:rule";

    final DqcSchedulerRulesRepository dqcSchedulerRulesRepository;
    final DqcSchedulerResultDataService dqcSchedulerResultDataService;
    final MeterRegistry meterRegistry;
    final CacheManager cm;
    private final Cache cache;

    public RuleMeterAspect(DqcSchedulerRulesRepository dqcSchedulerRulesRepository,
                           DqcSchedulerResultDataService dqcSchedulerResultDataService, MeterRegistry meterRegistry, CacheManager cm) {
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.dqcSchedulerResultDataService = dqcSchedulerResultDataService;
        this.meterRegistry = meterRegistry;
        this.cm = cm;
        this.cache= cm.getCache(CACHE_NAME_DQC);
    }

    @Around("execution(* com.qk.dm.dataquality.service.*.getWarnResultInfo(..))")
    public Object getWarnResultInfo(ProceedingJoinPoint pjp) throws Throwable {
        log.info("获取监控指标[{}]",pjp.getSignature().toShortString());
        Object[] args = pjp.getArgs();
        log.info("参数信息[{}]",args);
        String ruleId = (String) args[0];
        Optional<DqcSchedulerRules> schedulerRulesOptional = dqcSchedulerRulesRepository.findOne(QDqcSchedulerRules.dqcSchedulerRules.ruleId.eq(ruleId));
        DqcSchedulerRules dqcSchedulerRules = schedulerRulesOptional.orElse(null);
        if (Objects.nonNull(dqcSchedulerRules)){
            Optional<DqcSchedulerResultVO> firstResultVO = dqcSchedulerResultDataService.searchResultByTaskCode(dqcSchedulerRules.getTaskCode()).stream().findFirst();
            log.info("执行任务job[{}],执行规则id[{}],执行检查对象[{}]",
                    dqcSchedulerRules.getJobId(),
                    dqcSchedulerRules.getRuleId(),
                    String.join(
                            ",",
                            dqcSchedulerRules.getDatabaseName(),
                            dqcSchedulerRules.getTables(),
                            dqcSchedulerRules.getFields()));
            DqcSchedulerResultVO dqcSchedulerResultVO = firstResultVO.orElse(null);

            List<DqcSchedulerResultTitleVO> resultTitleList = Objects.requireNonNull(dqcSchedulerResultVO).getResultTitleList();
            List<Map<String, Object>> resultDataList = Objects.requireNonNull(dqcSchedulerResultVO).getResultDataList();

            Map<Object, List<Map<String, Object>>> objectListMap = resultDataList.stream()
                    .collect(
                            Collectors.groupingBy(it ->
                                    Optional.ofNullable(it.get("targetObj")).orElse("")));
            objectListMap.forEach((targetObj,rtList)-> rtList.forEach(it->
                    meterRegistryGauge(dqcSchedulerRules, resultTitleList, targetObj, it)));
        }
        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        log.info("返回值数据[{}]",retVal);
        return retVal;
    }

    /**
     * 添加监控指标
     * @param dqcSchedulerRules 质量调度规则
     * @param resultTitleList 结果数据头信息
     * @param targetObj 目标对象
     * @param resultMap 结果数据信息
     */
    private void meterRegistryGauge(DqcSchedulerRules dqcSchedulerRules, List<DqcSchedulerResultTitleVO> resultTitleList, Object targetObj, Map<String, Object> resultMap) {
        //需要按照target分组
        AtomicInteger ii = new AtomicInteger(-1);
        resultMap.forEach((k, v)->{
            int i = ii.incrementAndGet();
            DqcSchedulerResultTitleVO dqcSchedulerResultTitleVO = resultTitleList.get(i);
            log.info("执行标题【{}:{}】",dqcSchedulerResultTitleVO.getTitle(),dqcSchedulerResultTitleVO.getDataIndex());
            log.info("执行结果【{}:{}】",k,v);

            if (BigDecimalValidator.getInstance().isValid(v.toString())){
                BigDecimal b = new BigDecimal(v.toString());
                String metricName = metricName("result", "info");
                Tags tags = Tags.of(
                        TITLE, dqcSchedulerResultTitleVO.getTitle()+(StringUtils.isNotEmpty(targetObj.toString())?"": targetObj.toString()),
                        DATAINDEX, dqcSchedulerResultTitleVO.getDataIndex(),
                        TARGETOBJ, targetObj.toString(),
                        RULEID, String.valueOf(dqcSchedulerRules.getRuleTempId())
                        );
                final String cacheKey = tags.stream().map(Tag::getValue).collect(Collectors.joining(":"));
                cache.put(cacheKey,b);
                if (meterRegistry.find(metricName).tags(tags).meters().isEmpty()){
                    meterRegistry.gauge(
                            metricName,
                            tags,
                            cache,
                            c->
                                    new BigDecimal(
                                            String.valueOf(c.get(cacheKey,Object.class))
                                    ).doubleValue());
                }

            }
        });
    }

    public static String metricName(String... names) {
        Joiner joiner = Joiner.on(",");
        ArrayList<String> ts = Lists.newArrayList(NAME_RULE);
        ts.addAll(List.of(names));
        return  joiner.join(ts);
    }
}
