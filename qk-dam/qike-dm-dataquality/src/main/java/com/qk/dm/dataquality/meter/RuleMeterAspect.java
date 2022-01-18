package com.qk.dm.dataquality.meter;

import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import com.qk.dm.dataquality.entity.QDqcSchedulerRules;
import com.qk.dm.dataquality.repositories.DqcSchedulerRulesRepository;
import com.qk.dm.dataquality.service.DqcSchedulerResultDataService;
import com.qk.dm.dataquality.vo.DqcSchedulerResultTitleVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultVO;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
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

/**
 * @author zhudaoming
 */
@Aspect
@Component
@Slf4j
public class RuleMeterAspect {
    final DqcSchedulerRulesRepository dqcSchedulerRulesRepository;
    final DqcSchedulerResultDataService dqcSchedulerResultDataService;
    final MeterRegistry meterRegistry;
    final CacheManager cacheManager;
    private Cache cache;

    public RuleMeterAspect(DqcSchedulerRulesRepository dqcSchedulerRulesRepository,
                           DqcSchedulerResultDataService dqcSchedulerResultDataService, MeterRegistry meterRegistry, CacheManager cacheManager) {
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.dqcSchedulerResultDataService = dqcSchedulerResultDataService;
        this.meterRegistry = meterRegistry;
        this.cacheManager = cacheManager;
        this.cache= cacheManager.getCache("RuleMeter");
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
            AtomicInteger ii = new AtomicInteger(-1);
            resultDataList.forEach(it->{
                Object targetObj = it.entrySet().stream().filter(t -> "targetObj".equals(t.getKey())).map(Map.Entry::getValue).findFirst().orElse("");
                it.forEach((k,v)->{
                    int i = ii.incrementAndGet();
                    DqcSchedulerResultTitleVO dqcSchedulerResultTitleVO = resultTitleList.get(i);
                    log.info("执行标题【{}:{}】",dqcSchedulerResultTitleVO.getTitle(),dqcSchedulerResultTitleVO.getDataIndex());
                    log.info("执行结果【{}:{}】",k,v);

                    if (BigDecimalValidator.getInstance().isValid(v.toString())){
                        BigDecimal b = new BigDecimal(v.toString());
                        String metricName = RuleMeterConf.metricName("result", "info");
                        Tags tags = Tags.of(
                                "JOBID", dqcSchedulerRules.getJobId(),
                                "RULEID", dqcSchedulerRules.getRuleId(),
                                "TARGETOBJ", targetObj.toString(),
                                "DATAINDEX", dqcSchedulerResultTitleVO.getDataIndex(),
                                "TITLE", dqcSchedulerResultTitleVO.getTitle());
                        String cacheKey = tags.toString();
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
            });
        }
        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        log.info("返回值数据[{}]",retVal);
        return retVal;
    }

    //    @Pointcut("within(com.qk.dm.dataquality.service..*)")
    //    public void inServiceLayer() {}
    //
    //    @Pointcut("@annotation(com.qk.dm.dataquality.meter.Meter)")
    //    public void annotationMeter() {}
    //
    //    @Pointcut("inServiceLayer()&&annotationMeter()")
    //    private void operation() {}
    //
    //    @Around("operation()")
    //    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
    //        // start stopwatch
    //        Object retVal = pjp.proceed();
    //        // stop stopwatch
    //        return retVal;
    //    }
}
