package com.qk.dm.dataquality.service.impl;

import com.google.common.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.groovy.engine.shell.FactsScriptShell;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.NumberIndexEnum;
import com.qk.dm.dataquality.constant.RuleTypeEnum;
import com.qk.dm.dataquality.entity.*;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerResultRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerRulesRepository;
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
import java.text.DecimalFormat;
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
    public static final String DATA_INDEX_NAME_PREFIX = "dataIndexNamePrefix";
    public static final String LOCATIONS = "locations";

    public static final DecimalFormat df = new DecimalFormat("########0.00");
    public static final String NUMERICAL_VALUE = "率";

    private final QDqcSchedulerResult qDqcSchedulerResult = QDqcSchedulerResult.dqcSchedulerResult;

    private final DqcRuleTemplateRepository dqcRuleTemplateRepository;
    private final DqcSchedulerRulesRepository dqcSchedulerRulesRepository;
    private final DqcSchedulerResultRepository dqcSchedulerResultRepository;

    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DqcSchedulerResultDataServiceImpl(DqcRuleTemplateRepository dqcRuleTemplateRepository,
                                             DqcSchedulerRulesRepository dqcSchedulerRulesRepository,
                                             DqcSchedulerResultRepository dqcSchedulerResultRepository,
                                             EntityManager entityManager) {
        this.dqcRuleTemplateRepository = dqcRuleTemplateRepository;
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.dqcSchedulerResultRepository = dqcSchedulerResultRepository;
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
            //获取分页调度结果集信息
            resultDataMap = queryResultDataByParams(schedulerResultDataParamsVO);
            List<DqcSchedulerResult> dqcSchedulerResultList = (List<DqcSchedulerResult>) resultDataMap.get("list");
            total = (long) resultDataMap.get("total");
            //构建调度结果集
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

    @Override
    public Object getWarnResultInfo(String ruleId) {
        //获取执行规则信息
        Optional<DqcSchedulerRules> schedulerRulesOptional =
                dqcSchedulerRulesRepository.findOne(QDqcSchedulerRules.dqcSchedulerRules.ruleId.eq(ruleId));

        if (schedulerRulesOptional.isPresent()) {
            DqcSchedulerRules dqcSchedulerRules = schedulerRulesOptional.get();
            //获取规则参数位置信息
            Map<String, Object> locationMap = getLocationMap(schedulerRulesOptional);
            String dataIndexNamePrefix = (String) locationMap.get(DATA_INDEX_NAME_PREFIX);
            List<String> locations = (List<String>) locationMap.get(LOCATIONS);

            //字段列表信息
            String fieldStr = dqcSchedulerRules.getFields();
            List<String> fieldList = GsonUtil.fromJsonString(fieldStr, new TypeToken<List<String>>() {
            }.getType());

            //todo 模板结果定义
            //告警表达式
            String warnExpression = dqcSchedulerRules.getWarnExpression();
            if (!ObjectUtils.isEmpty(warnExpression)) {
                //todo ${1} >0
                //TODO 最近时间的记录 获取执行结果集
                Optional<DqcSchedulerResult> schedulerResultOptional =
                        dqcSchedulerResultRepository.findOne(QDqcSchedulerResult.dqcSchedulerResult.taskCode.eq(dqcSchedulerRules.getTaskCode()));
                if (schedulerResultOptional.isPresent()) {
                    DqcSchedulerResult dqcSchedulerResult = schedulerResultOptional.get();
                    String ruleResultStr = dqcSchedulerResult.getRuleResult();
                    //转换结果集
                    List<List<Object>> resultDataList = GsonUtil.fromJsonString(ruleResultStr, new TypeToken<List<List<Object>>>() {
                    }.getType());

                    for (int i = 0; i < fieldList.size(); i++) {
                        //每一列的执行结果
                        String fieldWarnExpression=warnExpression;
                        List<Object> fieldResult = resultDataList.get(i);
                        for (int j = 0; j < fieldResult.size(); j++) {
                            //${1} >0 && ${2}>10 ==>>10>0 && 5>10
                            fieldWarnExpression = fieldWarnExpression.replace("${" + j + 1 + "}", fieldResult.get(j).toString());
                        }
                        //执行表达式
                        boolean flag = false;
//                        if (dataIndexNamePrefix) {
//
//                        }
                    }


                }
            }
        }
        return null;
    }



    /**
     * 构建调度结果集
     */
    private void buildDqcSchedulerResults(List<DqcSchedulerResultVO> schedulerResultVOList, List<DqcSchedulerResult> dqcSchedulerResultList) {
        //解析模板结果定义参数信息
        Map<Long, List<DqcRuleTemplate>> ruleTemplateInfoMap = getRuleTemplateInfoMap(dqcSchedulerResultList);

        for (DqcSchedulerResult dqcSchedulerResult : dqcSchedulerResultList) {
            DqcRuleTemplate dqcRuleTemplate = ruleTemplateInfoMap.get(Long.valueOf(dqcSchedulerResult.getRuleTempId())).get(0);

            //模板定义信息 空值行数 : ${1}, 总行数 : ${2}, 空值率 : ${3}
            List<String> goalList = getGoalList(dqcRuleTemplate);

            //结果集构建
            DqcSchedulerResultVO.DqcSchedulerResultVOBuilder resultBuilder = DqcSchedulerResultVO.builder();
            resultBuilder.jobId(dqcSchedulerResult.getJobId());
            resultBuilder.jobName(dqcSchedulerResult.getJobName());
            resultBuilder.ruleId(dqcSchedulerResult.getRuleId());
            resultBuilder.ruleName(dqcSchedulerResult.getRuleName());
            resultBuilder.taskCode(dqcSchedulerResult.getTaskCode());
            resultBuilder.gmtModified(dqcSchedulerResult.getGmtModified());

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

    /**
     * 获取规则模板信息
     */
    private Map<Long, List<DqcRuleTemplate>> getRuleTemplateInfoMap(List<DqcSchedulerResult> dqcSchedulerResultList) {
        List<String> ruleTempIds = dqcSchedulerResultList.stream().map(DqcSchedulerResult::getRuleTempId).collect(Collectors.toList());

        List<Long> tempIdList = ruleTempIds.stream().map(Long::valueOf).collect(Collectors.toList());
        List<DqcRuleTemplate> ruleTemplateList = dqcRuleTemplateRepository.findAllById(tempIdList);

        return ruleTemplateList.stream().collect(Collectors.groupingBy(DqcRuleTemplate::getId));
    }

    /**
     * 模板定义信息
     */
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

    /**
     * 设置列的schema
     */
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

    /**
     * 设置列的信息
     */
    private List<Map<String, Object>> buildResultDataVOList(List<DqcSchedulerResultTitleVO> resultTitleVOList, String ruleResultStr, String ruleId) {
        List<Map<String, Object>> resultDataVOList = new ArrayList<>();
        //获取规则信息
        Optional<DqcSchedulerRules> schedulerRulesOptional = dqcSchedulerRulesRepository.findOne(QDqcSchedulerRules.dqcSchedulerRules.ruleId.eq(ruleId));

        if (schedulerRulesOptional.isPresent()) {
            //获取规则参数位置信息
            Map<String, Object> locationMap = getLocationMap(schedulerRulesOptional);
            String dataIndexNamePrefix = (String) locationMap.get(DATA_INDEX_NAME_PREFIX);
            List<String> locations = (List<String>) locationMap.get(LOCATIONS);

            //获取列 dataIndexList
            Map<String, Boolean> dataIndexMap = new LinkedHashMap<>();
            getDataIndexMap(resultTitleVOList, dataIndexMap);

            //转换结果集
            List<List<Object>> resultDataList = GsonUtil.fromJsonString(ruleResultStr, new TypeToken<List<List<Object>>>() {
            }.getType());

            //构建ResultData
            buildResultData(resultDataVOList, dataIndexNamePrefix, locations, dataIndexMap, resultDataList);
        }
        return resultDataVOList;
    }

    /**
     * 数值类型
     */
    private void getDataIndexMap(List<DqcSchedulerResultTitleVO> resultTitleVOList, Map<String, Boolean> dataIndexMap) {
        for (DqcSchedulerResultTitleVO resultTitleVO : resultTitleVOList) {
            if (resultTitleVO.getTitle().contains(NUMERICAL_VALUE) || NumberIndexEnum.getAllValue().contains(resultTitleVO.getTitle())) {
                dataIndexMap.put(resultTitleVO.getDataIndex(), true);
            } else {
                dataIndexMap.put(resultTitleVO.getDataIndex(), false);
            }
        }
    }

    /**
     * 获取规则参数位置信息
     */
    private Map<String, Object> getLocationMap(Optional<DqcSchedulerRules> schedulerRulesOptional) {
        Map<String, Object> locationMap = new HashMap<>();
        if (schedulerRulesOptional.isPresent()) {
            List<String> locations = new ArrayList<>();
            DqcSchedulerRules dqcSchedulerRules = schedulerRulesOptional.get();

            //表列表信息
            String tableStr = dqcSchedulerRules.getTables();
            List<String> tableList = GsonUtil.fromJsonString(tableStr, new TypeToken<List<String>>() {
            }.getType());
            String tablePart = String.join("&", tableList);

            //字段列表信息
            String fieldStr = dqcSchedulerRules.getFields();
            List<String> fieldList = GsonUtil.fromJsonString(fieldStr, new TypeToken<List<String>>() {
            }.getType());
            String fieldPart = String.join("&", fieldList);

            //获取具体列前缀key
            String dataIndexNamePrefix = "/" + dqcSchedulerRules.getDatabaseName() + "/" + tablePart + "/";

            //获取规则参数位置信息
            if (RuleTypeEnum.RULE_TYPE_TABLE.getCode().equalsIgnoreCase(dqcSchedulerRules.getRuleType())) {
                dataIndexNamePrefix = "/" + dqcSchedulerRules.getDatabaseName() + "/";
                locations.addAll(tableList);
            } else if (RuleTypeEnum.RULE_TYPE_FIELD.getCode().equalsIgnoreCase(dqcSchedulerRules.getRuleType())) {
                locations.addAll(fieldList);
                dataIndexNamePrefix = "/" + dqcSchedulerRules.getDatabaseName() + "/" + tablePart + "/";
            }

            locationMap.put(DATA_INDEX_NAME_PREFIX, dataIndexNamePrefix);
            locationMap.put(LOCATIONS, locations);
        }
        return locationMap;
    }

    /**
     * 构建ResultData
     */
    private void buildResultData(List<Map<String, Object>> resultDataVOList,
                                 String dataIndexNamePrefix,
                                 List<String> locations,
                                 Map<String, Boolean> dataIndexMap,
                                 List<List<Object>> resultDataList) {
        Set<String> dataIndexSet = dataIndexMap.keySet();
        List<String> dataIndexList = new ArrayList<>(dataIndexSet);
        if (resultDataList.size() > 0) {
            AtomicInteger dataIndex = new AtomicInteger(0);
            for (List data : resultDataList) {
                Map<String, Object> resultDataMap = new LinkedHashMap<>();
                //设置列具体名称
                String fieldName = locations.get(dataIndex.get());
                String dataIndexName = dataIndexNamePrefix + fieldName;
                resultDataMap.put(dataIndexList.get(0), dataIndexName);

                AtomicInteger valueIndex = new AtomicInteger(1);
                //获取指标数值
                getDataValue(dataIndexMap, dataIndexList, data, resultDataMap, valueIndex);

                resultDataVOList.add(resultDataMap);
                dataIndex.getAndIncrement();
            }
        }
    }

    /**
     * 获取指标数值
     */
    private void getDataValue(Map<String, Boolean> dataIndexMap, List<String> dataIndexList, List data, Map<String, Object> resultDataMap, AtomicInteger valueIndex) {
        for (Object value : data) {
            String dataIndexKey = dataIndexList.get(valueIndex.get());
            Boolean isDouble = dataIndexMap.get(dataIndexKey);
            //是否为Double数据类型
            if (isDouble) {
                value = df.format((Double) value * 100);
            }
            resultDataMap.put(dataIndexKey, value);
            valueIndex.getAndIncrement();
        }
    }


    /**
     * 分页查询规则结果集
     */
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

    /**
     * 查询条件
     */
    public void checkCondition(BooleanBuilder booleanBuilder,
                               QDqcSchedulerResult qDqcSchedulerResult,
                               DqcSchedulerResultParamsVO schedulerResultParamsVO) {

        if (!ObjectUtils.isEmpty(schedulerResultParamsVO.getJobId())) {
            booleanBuilder.and(qDqcSchedulerResult.jobId.eq(schedulerResultParamsVO.getJobId()));
        }

        if (!ObjectUtils.isEmpty(schedulerResultParamsVO.getJobName())) {
            booleanBuilder.and(qDqcSchedulerResult.jobName.eq(schedulerResultParamsVO.getJobName()));
        }

        if (!ObjectUtils.isEmpty(schedulerResultParamsVO.getRuleId())) {
            booleanBuilder.and(qDqcSchedulerResult.ruleId.eq(schedulerResultParamsVO.getRuleId()));
        }

        if (!ObjectUtils.isEmpty(schedulerResultParamsVO.getTaskCode())) {
            booleanBuilder.and(qDqcSchedulerResult.taskCode.eq(schedulerResultParamsVO.getTaskCode()));
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
