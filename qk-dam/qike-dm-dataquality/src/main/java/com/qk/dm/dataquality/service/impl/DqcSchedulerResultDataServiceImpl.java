package com.qk.dm.dataquality.service.impl;

import com.google.common.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.groovy.engine.GroovyShellExecutor;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.NumberIndexEnum;
import com.qk.dm.dataquality.constant.RuleTypeEnum;
import com.qk.dm.dataquality.entity.*;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerResultRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerRulesRepository;
import com.qk.dm.dataquality.service.DqcSchedulerResultDataService;
import com.qk.dm.dataquality.service.DqcSchedulerRulesService;
import com.qk.dm.dataquality.vo.DqcSchedulerResultPageVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultParamsVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultTitleVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private final static Logger LOG = LoggerFactory.getLogger(DqcSchedulerResultDataServiceImpl.class);

    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String DEFAULT_TITLE = "目标对象";
    public static final String DEFAULT_DATA_INDEX = "targetObj";
    public static final String RESULT_KEY = "result";
    public static final String DATA_INDEX_NAME_PREFIX = "dataIndexNamePrefix";
    public static final String META_DATA_LIST = "metaDataList";

    public static final DecimalFormat df = new DecimalFormat("########0.00");
    public static final String NUMERICAL_VALUE = "率";
    public static final int PERCENTAGE = 100;
    public static final int DECIMAL_PLACE = 6;
    public static final String DECIMAL_POINT = ".";
    public static final String SLASH = "/";
    public static final String UNDEFINED = "";

    private final QDqcSchedulerResult qDqcSchedulerResult = QDqcSchedulerResult.dqcSchedulerResult;
    private final QDqcSchedulerBasicInfo qDqcSchedulerBasicInfo = QDqcSchedulerBasicInfo.dqcSchedulerBasicInfo;

    private final DqcRuleTemplateRepository dqcRuleTemplateRepository;
    private final DqcSchedulerRulesRepository dqcSchedulerRulesRepository;
    private final DqcSchedulerResultRepository dqcSchedulerResultRepository;
    private final DqcSchedulerRulesService dqcSchedulerRulesService;

    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DqcSchedulerResultDataServiceImpl(DqcRuleTemplateRepository dqcRuleTemplateRepository,
                                             DqcSchedulerRulesRepository dqcSchedulerRulesRepository,
                                             DqcSchedulerResultRepository dqcSchedulerResultRepository,
                                             DqcSchedulerRulesService dqcSchedulerRulesService,
                                             EntityManager entityManager) {
        this.dqcRuleTemplateRepository = dqcRuleTemplateRepository;
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.dqcSchedulerResultRepository = dqcSchedulerResultRepository;
        this.dqcSchedulerRulesService = dqcSchedulerRulesService;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    /*************************************************分页查询规则结果集列表信息*********************************************************/
    @Override
    public PageResultVO<DqcSchedulerResultVO> getResultDataList(DqcSchedulerResultParamsVO schedulerResultDataParamsVO) {
        LOG.info("分页查询规则结果集列表信息,参数信息:【{}】", schedulerResultDataParamsVO);
        List<DqcSchedulerResultVO> schedulerResultVOList = new ArrayList<>();
        long total;
        //基础信息查询
        Map<String, Object> resultDataMap = null;
        try {
            //获取分页调度结果集信息
            resultDataMap = queryResultDataByParams(schedulerResultDataParamsVO);
            List<DqcSchedulerResult> dqcSchedulerResultList = (List<DqcSchedulerResult>) resultDataMap.get("list");
            total = (long) resultDataMap.get("total");
            LOG.info("查询结果集表成功");
            //构建调度结果集
            LOG.info("开始构建调度结果集");
            buildDqcSchedulerResults(schedulerResultVOList, dqcSchedulerResultList);
            LOG.info("成功构建调度结果集");
        } catch (Exception e) {
            LOG.info("分页查询规则结果集列表信息失败!!！");
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
    public List<DqcSchedulerResultVO> searchResultByTaskCode(Long taskCode) {
        List<DqcSchedulerResultVO> schedulerResultVOList = new ArrayList<>();
        List<DqcSchedulerResult> dqcSchedulerResultList = new ArrayList<>();
        //根据规则查询最新的执行结果集信息
        DqcSchedulerResult dqcSchedulerResult = dqcSchedulerResultRepository.findOneByLastTime(taskCode);
        dqcSchedulerResultList.add(dqcSchedulerResult);
        //构建调度结果集
        LOG.info("开始构建调度结果集");
        buildDqcSchedulerResults(schedulerResultVOList, dqcSchedulerResultList);
        LOG.info("成功构建调度结果集");
        return schedulerResultVOList;
    }


    /**
     * 构建调度结果集
     */
    private void buildDqcSchedulerResults(List<DqcSchedulerResultVO> schedulerResultVOList, List<DqcSchedulerResult> dqcSchedulerResultList) {
        //解析模板结果定义参数信息
        Map<Long, List<DqcRuleTemplate>> ruleTemplateInfoMap = getRuleTemplateInfoMap(dqcSchedulerResultList);
        LOG.info("成功获取规则模板信息,模板ID集合:【{}】", ruleTemplateInfoMap.keySet());
        for (DqcSchedulerResult dqcSchedulerResult : dqcSchedulerResultList) {
            DqcSchedulerResultVO dqcSchedulerResultVO = getDqcSchedulerResultVO(ruleTemplateInfoMap, dqcSchedulerResult);
            schedulerResultVOList.add(dqcSchedulerResultVO);
        }
    }

    /**
     * 获取调度执行结果集
     */
    private DqcSchedulerResultVO getDqcSchedulerResultVO(Map<Long, List<DqcRuleTemplate>> ruleTemplateInfoMap, DqcSchedulerResult dqcSchedulerResult) {
        DqcRuleTemplate dqcRuleTemplate = ruleTemplateInfoMap.get(Long.valueOf(dqcSchedulerResult.getRuleTempId())).get(0);
        //模板定义信息 空值行数 : ${1}, 总行数 : ${2}, 空值率 : ${3}
        List<String> tempResultList = getTempResultList(dqcRuleTemplate);
        //结果集基础信息
        DqcSchedulerResultVO.DqcSchedulerResultVOBuilder resultBuilder = DqcSchedulerResultVO.builder();
        resultBuilder.jobId(dqcSchedulerResult.getJobId());
        resultBuilder.jobName(dqcSchedulerResult.getJobName());
        resultBuilder.ruleId(dqcSchedulerResult.getRuleId());
        resultBuilder.ruleName(dqcSchedulerResult.getRuleName());
        resultBuilder.taskCode(dqcSchedulerResult.getTaskCode());
        resultBuilder.warnResult(dqcSchedulerResult.getWarnResult());
        resultBuilder.gmtModified(dqcSchedulerResult.getGmtModified());
        //设置列的schema
        List<DqcSchedulerResultTitleVO> resultTitleVOList = buildResultTitleVOList(tempResultList);
        resultBuilder.resultTitleList(resultTitleVOList);
        //设置列的信息
        resultBuilder.resultDataList(
                buildResultDataVOList(resultTitleVOList, dqcSchedulerResult, dqcSchedulerResult.getRuleId()));
        return resultBuilder.build();
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
    private List<String> getTempResultList(DqcRuleTemplate dqcRuleTemplate) {
        List<String> tempResultList = new LinkedList<>();
        String tempResult = dqcRuleTemplate.getTempResult();
        String[] tempResultArr = tempResult.split(COMMA);
        for (String tempResultStr : tempResultArr) {
            //空值行数 : ${1}
            String[] goalArr = tempResultStr.split(COLON);
            String goalStr = goalArr[0].trim();
            tempResultList.add(goalStr);
        }
        return tempResultList;
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
    private List<Map<String, Object>> buildResultDataVOList(List<DqcSchedulerResultTitleVO> resultTitleVOList, DqcSchedulerResult dqcSchedulerResult, String ruleId) {
        List<Map<String, Object>> resultDataVOList = new ArrayList<>();
        //获取规则信息
        Optional<DqcSchedulerRules> schedulerRulesOptional = dqcSchedulerRulesRepository.findOne(QDqcSchedulerRules.dqcSchedulerRules.ruleId.eq(ruleId));

        if (schedulerRulesOptional.isPresent()) {
            //获取规则参数位置信息
            String dataIndexNamePrefix = getDataIndexNamePrefix(schedulerRulesOptional);

            //获取列 dataIndexList
            Map<String, Boolean> dataIndexMap = new LinkedHashMap<>();
            getDataIndexMap(resultTitleVOList, dataIndexMap);

            //构建ResultData
            buildResultData(resultDataVOList, dataIndexNamePrefix, dataIndexMap, dqcSchedulerResult);
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
    private String getDataIndexNamePrefix(Optional<DqcSchedulerRules> schedulerRulesOptional) {
        //获取具体列前缀key
        String dataIndexNamePrefix = "";
        if (schedulerRulesOptional.isPresent()) {
            DqcSchedulerRules dqcSchedulerRules = schedulerRulesOptional.get();
            //表列表信息
            String tableStr = dqcSchedulerRules.getTables();
            if (!ObjectUtils.isEmpty(tableStr)) {
                List<String> tableList = GsonUtil.fromJsonString(tableStr, new TypeToken<List<String>>() {
                }.getType());
                String tablePart = String.join("&", tableList);

                //获取规则参数位置信息
                if (RuleTypeEnum.RULE_TYPE_TABLE.getCode().equalsIgnoreCase(dqcSchedulerRules.getRuleType())) {
                    dataIndexNamePrefix = dqcSchedulerRules.getDatabaseName() + SLASH;
                } else if (RuleTypeEnum.RULE_TYPE_FIELD.getCode().equalsIgnoreCase(dqcSchedulerRules.getRuleType())) {
                    dataIndexNamePrefix = dqcSchedulerRules.getDatabaseName() + DECIMAL_POINT + tablePart + DECIMAL_POINT;
                }
            } else {
                //规则信息不设置元数据信息
                dataIndexNamePrefix = SLASH;
            }
        }
        return dataIndexNamePrefix;
    }

    /**
     * 构建ResultData
     */
    private void buildResultData(List<Map<String, Object>> resultDataVOList,
                                 String dataIndexNamePrefix,
                                 Map<String, Boolean> dataIndexMap,
                                 DqcSchedulerResult dqcSchedulerResult) {
        //获取结果集数据
        List<List<Object>> resultDataList = getResultDataList(dqcSchedulerResult);

        //获取结果集规则元数据信息
        List<String> metaDataList = getMetaDataList(dqcSchedulerResult);

        Set<String> dataIndexSet = dataIndexMap.keySet();
        List<String> dataIndexList = new ArrayList<>(dataIndexSet);
        if (metaDataList != null) {
            //解析批量获取指标值,设置元数据信息
            bulkResultDataWithMetaData(resultDataVOList, dataIndexNamePrefix, dataIndexMap, resultDataList, metaDataList, dataIndexList);
        } else {
            //解析批量获取指标值,未设置元数据信息
            bulkResultDataNoMetaData(resultDataVOList, UNDEFINED, dataIndexMap, resultDataList, dataIndexList);
        }
    }

    /**
     * 获取结果集数据
     */
    private List<List<Object>> getResultDataList(DqcSchedulerResult dqcSchedulerResult) {
        //获取结果集
        String ruleResultStr = dqcSchedulerResult.getRuleResult();
        return GsonUtil.fromJsonString(ruleResultStr, new TypeToken<List<List<Object>>>() {
        }.getType());
    }

    /**
     * 获取结果集元数据信息
     */
    private List<String> getMetaDataList(DqcSchedulerResult dqcSchedulerResult) {
        String ruleMetaData = dqcSchedulerResult.getRuleMetaData();
        if (!ObjectUtils.isEmpty(ruleMetaData)) {
            return Arrays.asList(ruleMetaData.split(","));
        }
        return null;
    }

    /**
     * 解析批量获取指标值,设置元数据信息
     */
    private void bulkResultDataWithMetaData(List<Map<String, Object>> resultDataVOList, String dataIndexNamePrefix, Map<String, Boolean> dataIndexMap, List<List<Object>> resultDataList, List<String> metaDataList, List<String> dataIndexList) {
        //设置元数据信息
        AtomicInteger fieldAtomic = new AtomicInteger(0);
        for (String metaData : metaDataList) {
            int fieldIndex = fieldAtomic.getAndIncrement();
            //设置列具体名称
            String dataIndexName = dataIndexNamePrefix + metaData;
            //获取字段对应的结果集
            List<Object> resultData = null;
            if (fieldIndex > resultDataList.size() - 1) {
                //未能准备匹配.默认取首位数据
                resultData = resultDataList.get(0);
            } else {
                //正确匹配
                resultData = resultDataList.get(fieldIndex);
            }
            //数据存储
            Map<String, Object> resultDataMap = new LinkedHashMap<>();
            resultDataMap.put(dataIndexList.get(0), dataIndexName);
            //获取指标数值
            getDataValue(dataIndexMap, dataIndexList, resultData, resultDataMap);

            resultDataVOList.add(resultDataMap);
            fieldAtomic.getAndIncrement();
        }
    }

    /**
     * 解析批量获取指标值,未设置元数据信息
     */
    private void bulkResultDataNoMetaData(List<Map<String, Object>> resultDataVOList,
                                          String dataIndexName,
                                          Map<String, Boolean> dataIndexMap,
                                          List<List<Object>> resultDataList,
                                          List<String> dataIndexList) {
        AtomicInteger dataIndex = new AtomicInteger(0);
        for (List data : resultDataList) {
            //数据存储
            Map<String, Object> resultDataMap = new LinkedHashMap<>();
            //指标名称
            resultDataMap.put(dataIndexList.get(0), dataIndexName);

            //获取指标数值
            getDataValue(dataIndexMap, dataIndexList, data, resultDataMap);

            resultDataVOList.add(resultDataMap);
            dataIndex.getAndIncrement();
        }
    }

    /**
     * 获取指标数值
     */
    private void getDataValue(Map<String, Boolean> dataIndexMap, List<String> dataIndexList, List data, Map<String, Object> resultDataMap) {
        AtomicInteger valueIndex = new AtomicInteger(1);
        for (Object value : data) {
            //TODO 修改模板定义展示,可以考虑根据模板修订时间进行查询
            String dataIndexKey = dataIndexList.get(valueIndex.get());
            Boolean isDouble = dataIndexMap.get(dataIndexKey);
            //是否为Double数据类型
            if (isDouble) {
                BigDecimal bigDecimal = BigDecimal.valueOf((Double) value);
                value = bigDecimal.multiply(BigDecimal.valueOf(PERCENTAGE)).setScale(DECIMAL_PLACE, RoundingMode.FLOOR);
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

        if (!ObjectUtils.isEmpty(schedulerResultParamsVO.getWarnResult())) {
            booleanBuilder.and(qDqcSchedulerResult.warnResult.eq(schedulerResultParamsVO.getWarnResult()));
        }

        if (!StringUtils.isEmpty(schedulerResultParamsVO.getBeginDay())
                && !StringUtils.isEmpty(schedulerResultParamsVO.getEndDay())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDqcSchedulerResult.gmtModified);
            booleanBuilder.and(
                    dateExpr.between(schedulerResultParamsVO.getBeginDay(), schedulerResultParamsVO.getEndDay()));
        }
    }

    /*************************************************告警表达式执行*********************************************************/
    @Override
    public Object getWarnResultInfo(String ruleId) {
        LOG.info("开始获取告警表达式执行结果,ruleId: 【{}】", ruleId);
        //获取执行规则信息
        Optional<DqcSchedulerRules> schedulerRulesOptional = dqcSchedulerRulesRepository.findOne(QDqcSchedulerRules.dqcSchedulerRules.ruleId.eq(ruleId));

        if (schedulerRulesOptional.isPresent()) {
            LOG.info("ruleId: 【{}】,查询到对应的规则信息", ruleId);
            DqcSchedulerRules dqcSchedulerRules = schedulerRulesOptional.get();
            //告警表达式
            String warnExpression = dqcSchedulerRules.getWarnExpression();
            LOG.info("ruleId: 【{}】,所对应的告警表达式: 【{}】 ", ruleId, warnExpression);
            if (!ObjectUtils.isEmpty(warnExpression)) {
                return getExecuteWarnResult(dqcSchedulerRules);
            } else {
                throw new BizException("未配置告警表达式!!!");
            }
        }
        return null;
    }

    /**
     * 执行告警表达式,获取告警表达式结果
     */
    private Object getExecuteWarnResult(DqcSchedulerRules dqcSchedulerRules) {
        LOG.info("开始执行告警表达式:【{}】 ,获取告警表达式结果 ", dqcSchedulerRules.getWarnExpression());
        String executeWarnResult = "";
        //获取执行结果集,最新时间的记录
        DqcSchedulerResult dqcSchedulerResult = dqcSchedulerResultRepository.findOneByLastTime(dqcSchedulerRules.getTaskCode());
        LOG.info("查询到taskCode:【{}】 ,最新时间记录的结果信息 ", dqcSchedulerRules.getTaskCode());
        if (ObjectUtils.isEmpty(dqcSchedulerRules.getTables()) || ObjectUtils.isEmpty(dqcSchedulerRules.getFields())) {
            //自定义模板规则不带有元数据信息,执行告警表达式
            LOG.info("自定义模板规则不带有元数据信息,执行告警表达式");
            executeWarnResult = executeWarnExpressionNoMetaData(dqcSchedulerRules, dqcSchedulerResult);
        } else {
            //规则带有元数据信息,执行告警表达式
            LOG.info("规则带有元数据信息,执行告警表达式");
            executeWarnResult = executeWarnExpression(dqcSchedulerRules, dqcSchedulerResult);
        }
        LOG.info("成功执行告警表达式,获取告警表达式结果,executeWarnResult: 【{}】", executeWarnResult);
        return executeWarnResult;
    }

    /**
     * 自定义模板规则不带有元数据信息,执行告警表达式
     */
    private String executeWarnExpressionNoMetaData(DqcSchedulerRules dqcSchedulerRules, DqcSchedulerResult dqcSchedulerResult) {
        if (dqcSchedulerResult != null) {
            Map<String, List<Object>> dataMap = new HashMap<>(16);
            //获取结果集元数据信息
            List<String> metaDataList = getMetaDataList(dqcSchedulerResult);
            //获取结果集数据
            List<List<Object>> resultDataList = getResultDataList(dqcSchedulerResult);
            for (String metaData : metaDataList) {
                dataMap.put(metaData, resultDataList.get(0));
            }
            //执行告警表达式
            try {
                LOG.info("自定义模板,执行告警表达式,字段信息: 【{}】", metaDataList);
                return String.valueOf(GroovyShellExecutor.evaluateBinding(metaDataList, dataMap, dqcSchedulerRules.getWarnExpression())).toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.info("自定义模板,告警表达式执行失败,ruleId: 【{}】", dqcSchedulerRules.getRuleId());
                throw new BizException("告警表达式执行失败!!!");
            }
        } else {
            LOG.info("自定义模板,告警表达式执行失败,ruleId: 【{}】", dqcSchedulerRules.getRuleId());
            throw new BizException("规则结果集不存在!!!");
        }
    }

    /**
     * 规则带有元数据信息,执行告警表达式
     */
    private String executeWarnExpression(DqcSchedulerRules dqcSchedulerRules, DqcSchedulerResult dqcSchedulerResult) {
        if (dqcSchedulerResult != null) {
            Map<String, List<Object>> dataMap = new HashMap<>(16);
            //获取结果集元数据信息
            List<String> metaDataList = getMetaDataList(dqcSchedulerResult);
            //获取结果集数据
            AtomicInteger index = new AtomicInteger(0);
            List<List<Object>> resultDataList = getResultDataList(dqcSchedulerResult);
            for (String metaData : metaDataList) {
                //设置告警表达式字段,及其关联结果集
                dataMap.put(metaData, resultDataList.get(index.get()));
                index.getAndIncrement();
            }
            //执行告警表达式
            try {
                LOG.info("执行告警表达式,字段信息: 【{}】", metaDataList);
                return String.valueOf(GroovyShellExecutor.evaluateBinding(metaDataList, dataMap, dqcSchedulerRules.getWarnExpression())).toLowerCase();
            } catch (Exception e) {
                LOG.info("告警表达式执行失败,ruleId: 【{}】", dqcSchedulerRules.getRuleId());
                e.printStackTrace();
                throw new BizException("告警表达式执行失败!!!");
            }
        } else {
            LOG.info("告警表达式执行失败,ruleId: 【{}】", dqcSchedulerRules.getRuleId());
            throw new BizException("规则结果集不存在!!!");
        }
    }

    /****************************************************根据分类目录获取告警结果*****************************************************/

    @Override
    public List<DqcSchedulerResult> getSchedulerResultList(Set<String> jobIds) {
        return (List<DqcSchedulerResult>) dqcSchedulerResultRepository.findAll(qDqcSchedulerResult.jobId.in(jobIds));
    }

    @Override
    public List<DqcSchedulerResult> getSchedulerResultListByWarn(String warnResult) {
        return (List<DqcSchedulerResult>) dqcSchedulerResultRepository.findAll(qDqcSchedulerResult.warnResult.eq(warnResult));
    }

    @Override
    public List<DqcSchedulerResult> getSchedulerResultListByWarnTrend(String warnResult, Date startDate, Date endDate) {
        return (List<DqcSchedulerResult>) dqcSchedulerResultRepository.findAll(qDqcSchedulerResult.warnResult.eq(warnResult).and(qDqcSchedulerResult.gmtCreate.between(startDate, endDate)));
    }

    @Override
    public PageResultVO<DqcSchedulerResultVO> searchResultPageList(DqcSchedulerResultPageVO schedulerResultDataParamsVO) {
        List<DqcSchedulerResultVO> schedulerResultVOList = new ArrayList<>();
        long total;
        //基础信息查询
        Map<String, Object> resultDataMap = null;
        try {
            //获取分页调度结果集信息
            resultDataMap = queryParams(schedulerResultDataParamsVO);
            List<DqcSchedulerResult> dqcSchedulerResultList = (List<DqcSchedulerResult>) resultDataMap.get("list");
            total = (long) resultDataMap.get("total");
            //构建调度结果集
            buildSchedulerResults(schedulerResultVOList, dqcSchedulerResultList);
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

    private void buildSchedulerResults(List<DqcSchedulerResultVO> schedulerResultVOList, List<DqcSchedulerResult> dqcSchedulerResultList) {
        //解析模板结果定义参数信息
        Map<Long, List<DqcRuleTemplate>> ruleTemplateInfoMap = getRuleTemplateInfoMap(dqcSchedulerResultList);
        //获取所涉及规则
        Map<Long, List<DqcSchedulerRules>> schedulerRulesMap = getSchedulerRulesMap(dqcSchedulerResultList);
        for (DqcSchedulerResult dqcSchedulerResult : dqcSchedulerResultList) {
            DqcSchedulerResultVO dqcSchedulerResultVO = getDqcSchedulerResultVO(ruleTemplateInfoMap, dqcSchedulerResult);
            //添加所涉及表字段
            DqcSchedulerRules dqcSchedulerRules = schedulerRulesMap.get(dqcSchedulerResult.getTaskCode()).get(0);
            dqcSchedulerResultVO.setDatabaseName(dqcSchedulerRules.getDatabaseName());
            dqcSchedulerResultVO.setTableList(dqcSchedulerRules.getTables() != null ? DqcConstant.jsonStrToList(dqcSchedulerRules.getTables()) : null);
            dqcSchedulerResultVO.setFieldList(dqcSchedulerRules.getFields() != null ? DqcConstant.jsonStrToList(dqcSchedulerRules.getFields()) : null);
            schedulerResultVOList.add(dqcSchedulerResultVO);
        }
    }

    private Map<Long, List<DqcSchedulerRules>> getSchedulerRulesMap(List<DqcSchedulerResult> dqcSchedulerResultList) {
        //拿到所有的rules
        Set<Long> taskCodeSet = dqcSchedulerResultList.stream().map(DqcSchedulerResult::getTaskCode).collect(Collectors.toSet());
        List<DqcSchedulerRules> schedulerRulesListByTaskCode = dqcSchedulerRulesService.getSchedulerRulesListByTaskCode(taskCodeSet);
        return schedulerRulesListByTaskCode.stream().collect(Collectors.groupingBy(DqcSchedulerRules::getTaskCode));
    }

    private Map<String, Object> queryParams(DqcSchedulerResultPageVO schedulerResultParamsVO) {
        Map<String, Object> result = new HashMap<>();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, schedulerResultParamsVO);
        long count =
                jpaQueryFactory
                        .select(qDqcSchedulerResult.count())
                        .from(qDqcSchedulerResult)
                        .innerJoin(qDqcSchedulerBasicInfo)
                        .on(qDqcSchedulerResult.jobId.eq(qDqcSchedulerBasicInfo.jobId))
                        .where(booleanBuilder)
                        .fetchOne();
        List<DqcSchedulerResult> dqcSchedulerResultList =
                jpaQueryFactory
                        .select(qDqcSchedulerResult)
                        .from(qDqcSchedulerResult)
                        .innerJoin(qDqcSchedulerBasicInfo)
                        .on(qDqcSchedulerResult.jobId.eq(qDqcSchedulerBasicInfo.jobId))
                        .where(booleanBuilder)
                        .orderBy(qDqcSchedulerResult.gmtModified.desc())
                        .offset((schedulerResultParamsVO.getPagination().getPage() - 1) * schedulerResultParamsVO.getPagination().getSize())
                        .limit(schedulerResultParamsVO.getPagination().getSize())
                        .fetch();

        result.put("list", dqcSchedulerResultList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, DqcSchedulerResultPageVO schedulerResultParamsVO) {
        if (!ObjectUtils.isEmpty(schedulerResultParamsVO.getJobName())) {
            booleanBuilder.and(qDqcSchedulerResult.jobName.contains(schedulerResultParamsVO.getJobName()));
        }
        if (!ObjectUtils.isEmpty(schedulerResultParamsVO.getWarnResult())) {
            booleanBuilder.and(qDqcSchedulerResult.warnResult.eq(schedulerResultParamsVO.getWarnResult()));
        }
        if (!ObjectUtils.isEmpty(schedulerResultParamsVO.getDirId())) {
            booleanBuilder.and(qDqcSchedulerBasicInfo.dirId.eq(schedulerResultParamsVO.getDirId()));
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
