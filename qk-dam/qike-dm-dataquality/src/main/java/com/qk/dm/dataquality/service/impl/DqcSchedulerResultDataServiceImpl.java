package com.qk.dm.dataquality.service.impl;

import com.google.common.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.entity.DqcSchedulerResult;
import com.qk.dm.dataquality.entity.QDqcSchedulerResult;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.service.DqcSchedulerResultDataService;
import com.qk.dm.dataquality.vo.DqcSchedulerResultParamsVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultTitleVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 调度结果集
 *
 * @author wjq
 * @date 2021/12/10
 * @since 1.0.0
 */
@Service
public class DqcSchedulerResultDataServiceImpl implements DqcSchedulerResultDataService {
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String DEFAULT_TITLE = "目标对象";
    public static final String DEFAULT_DATA_INDEX = "targetObj";
    public static final String RESULT_KEY = "result";

    private final QDqcSchedulerResult qDqcSchedulerResult = QDqcSchedulerResult.dqcSchedulerResult;

    private final DqcRuleTemplateRepository dqcRuleTemplateRepository;

    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DqcSchedulerResultDataServiceImpl(DqcRuleTemplateRepository dqcRuleTemplateRepository, EntityManager entityManager) {
        this.dqcRuleTemplateRepository = dqcRuleTemplateRepository;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public PageResultVO<DqcSchedulerResultVO> getResultDataList(DqcSchedulerResultParamsVO schedulerResultDataParamsVO) {
        List<DqcSchedulerResultVO> schedulerResultVOList = new ArrayList<>();
        long total;
        //基础信息查询
        Map<String, Object> resultDataMap = null;
        try {
            resultDataMap = queryResultDataByParams(schedulerResultDataParamsVO);
            List<DqcSchedulerResult> dqcSchedulerResultList = (List<DqcSchedulerResult>) resultDataMap.get("list");
            total = (long) resultDataMap.get("total");

            buildDqcSchedulerResults(schedulerResultVOList, dqcSchedulerResultList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        return new PageResultVO<>(
                total,
                schedulerResultDataParamsVO.getPagination().getPage(),
                schedulerResultDataParamsVO.getPagination().getSize(),
                schedulerResultVOList);
    }

    private void buildDqcSchedulerResults(List<DqcSchedulerResultVO> schedulerResultVOList, List<DqcSchedulerResult> dqcSchedulerResultList) {
        //解析模板结果定义参数信息
        Map<Long, List<DqcRuleTemplate>> ruleTemplateInfoMap = getRuleTemplateInfoMap(dqcSchedulerResultList);

        for (DqcSchedulerResult dqcSchedulerResult : dqcSchedulerResultList) {
            DqcRuleTemplate dqcRuleTemplate = ruleTemplateInfoMap.get(Long.valueOf(dqcSchedulerResult.getRuleTempId())).get(0);

            //模板定义信息 空值行数 : ${1}, 总行数 : ${2}, 空值率 : ${3}
            List<String> goalList = getGoalList(dqcRuleTemplate);

            //结果集构建
            DqcSchedulerResultVO.DqcSchedulerResultVOBuilder resultBuilder = DqcSchedulerResultVO.builder();
            resultBuilder.jobName(dqcSchedulerResult.getJobName());
            resultBuilder.ruleId(dqcSchedulerResult.getRuleId());

            //设置列的schema
            List<DqcSchedulerResultTitleVO> resultTitleVOList = buildResultTitleVOList(goalList);
            resultBuilder.resultTitleList(resultTitleVOList);

            //设置列的信息
            resultBuilder.resultDataList(
                    buildResultDataVOList(resultTitleVOList, dqcSchedulerResult.getRuleResult(), dqcSchedulerResult.getRuleId()));

            DqcSchedulerResultVO dqcSchedulerResultVO = resultBuilder.build();
            schedulerResultVOList.add(dqcSchedulerResultVO);
        }
    }

    private Map<Long, List<DqcRuleTemplate>> getRuleTemplateInfoMap(List<DqcSchedulerResult> dqcSchedulerResultList) {
        List<String> ruleTempIds = dqcSchedulerResultList.stream().map(DqcSchedulerResult::getRuleTempId).collect(Collectors.toList());

        List<Long> tempIdList = ruleTempIds.stream().map(Long::valueOf).collect(Collectors.toList());
        List<DqcRuleTemplate> ruleTemplateList = dqcRuleTemplateRepository.findAllById(tempIdList);

        return ruleTemplateList.stream().collect(Collectors.groupingBy(DqcRuleTemplate::getId));
    }

    private List<String> getGoalList(DqcRuleTemplate dqcRuleTemplate) {
        List<String> goalList = new LinkedList<>();
        String tempResult = dqcRuleTemplate.getTempResult();
        String[] tempResultArr = tempResult.split(COMMA);
        for (String tempResultStr : tempResultArr) {
            //空值行数 : ${1}
            String[] goalArr = tempResultStr.split(COLON);
            String goalStr = goalArr[0].trim();
            goalList.add(goalStr);
        }
        return goalList;
    }

    private List<DqcSchedulerResultTitleVO> buildResultTitleVOList(List<String> goalList) {
        List<DqcSchedulerResultTitleVO> schedulerResultTitleVOList = new LinkedList<>();

        //默认 目标对象
        DqcSchedulerResultTitleVO schedulerResultTitleVO = DqcSchedulerResultTitleVO.builder().title(DEFAULT_TITLE).dataIndex(DEFAULT_DATA_INDEX).build();
        schedulerResultTitleVOList.add(schedulerResultTitleVO);

        AtomicInteger index = new AtomicInteger(1);
        for (String goal : goalList) {
            DqcSchedulerResultTitleVO resultTitleVO =
                    DqcSchedulerResultTitleVO.builder()
                            .title(goal)
                            .dataIndex(RESULT_KEY + index.get())
                            .build();
            schedulerResultTitleVOList.add(resultTitleVO);
            index.getAndIncrement();
        }

        return schedulerResultTitleVOList;
    }

    private List<Map<String, Object>> buildResultDataVOList(List<DqcSchedulerResultTitleVO> resultTitleVOList, String ruleResultStr, String ruleId) {
        List<Map<String, Object>> resultDataVOList = new ArrayList<>();
        //获取列 dataIndexList
        List<String> dataIndexList = resultTitleVOList.stream().map(DqcSchedulerResultTitleVO::getDataIndex).collect(Collectors.toList());

        //转换结果集
        List<List<Object>> resultDataList = GsonUtil.fromJsonString(ruleResultStr, new TypeToken<List<List<Object>>>() {
        }.getType());

        if (resultDataList.size() > 0) {
            for (List data : resultDataList) {
                Map<String, Object> resultDataMap = new LinkedHashMap<>();
                resultDataMap.put(dataIndexList.get(0), ruleId);

                AtomicInteger index = new AtomicInteger(1);

                for (Object value : data) {
                    resultDataMap.put(dataIndexList.get(index.get()), value);
                    index.getAndIncrement();
                }
                resultDataVOList.add(resultDataMap);
            }
        }
        return resultDataVOList;
    }


    public Map<String, Object> queryResultDataByParams(DqcSchedulerResultParamsVO schedulerResultParamsVO) {

        Map<String, Object> result = new HashMap<>();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, qDqcSchedulerResult, schedulerResultParamsVO);
        long count =
                jpaQueryFactory
                        .select(qDqcSchedulerResult.count())
                        .from(qDqcSchedulerResult)
                        .where(booleanBuilder)
                        .fetchOne();
        List<DqcSchedulerResult> dqcSchedulerResultList =
                jpaQueryFactory
                        .select(qDqcSchedulerResult)
                        .from(qDqcSchedulerResult)
                        .where(booleanBuilder)
                        .orderBy(qDqcSchedulerResult.gmtModified.desc())
                        .offset((schedulerResultParamsVO.getPagination().getPage() - 1) * schedulerResultParamsVO.getPagination().getSize())
                        .limit(schedulerResultParamsVO.getPagination().getSize())
                        .fetch();

        result.put("list", dqcSchedulerResultList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder,
                               QDqcSchedulerResult qDqcSchedulerResult,
                               DqcSchedulerResultParamsVO schedulerResultParamsVO) {

        if (!ObjectUtils.isEmpty(schedulerResultParamsVO.getJobName())) {
            booleanBuilder.and(qDqcSchedulerResult.jobName.eq(schedulerResultParamsVO.getJobName()));
        }

        if (!StringUtils.isEmpty(schedulerResultParamsVO.getBeginDay())
                && !StringUtils.isEmpty(schedulerResultParamsVO.getEndDay())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDqcSchedulerResult.gmtModified);
            booleanBuilder.and(
                    dateExpr.between(schedulerResultParamsVO.getBeginDay(), schedulerResultParamsVO.getEndDay()));
        }
    }
}
