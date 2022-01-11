package com.qk.dm.dataquality.service.impl;

import com.google.common.collect.Lists;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.groovy.constant.FunctionConstant;
import com.qk.dam.groovy.facts.RuleFunctionGenerator;
import com.qk.dam.groovy.model.FactModel;
import com.qk.dam.groovy.model.RuleFunctionInfo;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.RuleTypeEnum;
import com.qk.dm.dataquality.constant.ScanTypeEnum;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import com.qk.dm.dataquality.entity.QDqcRuleTemplate;
import com.qk.dm.dataquality.entity.QDqcSchedulerRules;
import com.qk.dm.dataquality.groovy.RuleFunctionModelBuilder;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerRulesMapper;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerRulesRepository;
import com.qk.dm.dataquality.service.DqcRuleSqlBuilderService;
import com.qk.dm.dataquality.utils.GenerateSqlUtil;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成执行sql
 *
 * @author wjq
 * @date 2021/12/4
 * @since 1.0.0
 */
@Service
public class DqcRuleSqlBuilderServiceImpl implements DqcRuleSqlBuilderService {
    private final DqcRuleTemplateRepository dqcRuleTemplateRepository;
    private final DqcSchedulerRulesRepository dqcSchedulerRulesRepository;
    private final RuleFunctionModelBuilder ruleFunctionModelBuilder;


    @Autowired
    public DqcRuleSqlBuilderServiceImpl(DqcRuleTemplateRepository dqcRuleTemplateRepository,
                                        DqcSchedulerRulesRepository dqcSchedulerRulesRepository,
                                        RuleFunctionModelBuilder ruleFunctionModelBuilder) {
        this.dqcRuleTemplateRepository = dqcRuleTemplateRepository;
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.ruleFunctionModelBuilder = ruleFunctionModelBuilder;
    }


    @Override
    public Object getExecuteSql(DqcSchedulerRulesVO dqcSchedulerRulesVO) {
        String executeSql = null;
        //获取规则模板
        Optional<DqcRuleTemplate> dqcRuleTemplate = dqcRuleTemplateRepository.findOne(QDqcRuleTemplate.dqcRuleTemplate.id.eq(dqcSchedulerRulesVO.getRuleTempId()));

        if (dqcRuleTemplate.isPresent()) {
            StringBuilder sqlBuffer = new StringBuilder();
            //解析替换参数信息生成SQL
            dqcRuleTemplate.ifPresent(ruleTemplate -> buildExecuteSql(dqcSchedulerRulesVO, ruleTemplate, sqlBuffer));
            executeSql = sqlBuffer.toString();
            //sql特殊字符替换操作(todo 目前只有单引号)
//            executeSql = executeSql.replaceAll(GenerateSqlUtil.SINGLE_QUOTES, GenerateSqlUtil.SINGLE_QUOTES_MARK);
        } else {
            throw new BizException("未匹配到对应的规则模板信息!!!");
        }
        return executeSql;
    }

    @Override
    public Object getRealTimeSql(String ruleId) {
        String realTimeSql = null;
        //查询规则调度信息
        Optional<DqcSchedulerRules> schedulerRulesOptional =
                dqcSchedulerRulesRepository.findOne(QDqcSchedulerRules.dqcSchedulerRules.ruleId.eq(ruleId));
        if (schedulerRulesOptional.isPresent()) {
            //转换规则调度对象VO
            DqcSchedulerRules dqcSchedulerRules = schedulerRulesOptional.get();
            DqcSchedulerRulesVO dqcSchedulerRulesVO = DqcSchedulerRulesMapper.INSTANCE.userDqcSchedulerRulesVO(dqcSchedulerRules);
            dqcSchedulerRulesVO.setTableList(DqcConstant.jsonStrToList(dqcSchedulerRules.getTables()));
            dqcSchedulerRulesVO.setFieldList(DqcConstant.jsonStrToList(dqcSchedulerRules.getFields()));

            realTimeSql = (String) getExecuteSql(dqcSchedulerRulesVO);
        }
        return realTimeSql;
    }

    /**
     * 解析替换参数信息生成SQL
     *
     * @param dqcSchedulerRulesVO
     * @param dqcRuleTemplate
     * @param sqlBuffer
     */
    private void buildExecuteSql(DqcSchedulerRulesVO dqcSchedulerRulesVO, DqcRuleTemplate dqcRuleTemplate, StringBuilder sqlBuffer) {
        //规则类型
        String ruleType = dqcSchedulerRulesVO.getRuleType();
        //模板sql
        String tempSql = dqcRuleTemplate.getTempSql();

        //库级别规则
        if (RuleTypeEnum.RULE_TYPE_DB.getCode().equalsIgnoreCase(ruleType)) {
            ruleTypeDBSql(dqcSchedulerRulesVO, sqlBuffer, tempSql);
        }

        //表级别规则
        if (RuleTypeEnum.RULE_TYPE_TABLE.getCode().equalsIgnoreCase(ruleType)) {
            ruleTypeTableSql(dqcSchedulerRulesVO, sqlBuffer, tempSql);
        }

        //字段级别规则
        if (RuleTypeEnum.RULE_TYPE_FIELD.getCode().equalsIgnoreCase(ruleType)) {
            ruleTypeFieldSql(dqcSchedulerRulesVO, sqlBuffer, tempSql);
        }

        //TODO 获取模板SQL;需要考虑自定义模式的模板信息

    }

    /**
     * 生成库级规则sql
     *
     * @param dqcSchedulerRulesVO
     * @param sqlBuffer
     * @param tempSql
     */
    private void ruleTypeDBSql(DqcSchedulerRulesVO dqcSchedulerRulesVO, StringBuilder sqlBuffer, String tempSql) {
        ruleTypeTableSql(dqcSchedulerRulesVO, sqlBuffer, tempSql);
    }

    /**
     * 生成表级规则sql
     *
     * @param dqcSchedulerRulesVO
     * @param sqlBuffer
     * @param tempSql
     */
    private void ruleTypeTableSql(DqcSchedulerRulesVO dqcSchedulerRulesVO, StringBuilder sqlBuffer, String tempSql) {
        //表信息
        List<String> tableList = dqcSchedulerRulesVO.getTableList();

        //扫描方式(全部/条件)
        String wherePartSql = getScanSql(dqcSchedulerRulesVO);

        if (tableList != null) {
            AtomicInteger index = new AtomicInteger(0);
            for (String tableName : tableList) {
                Map<String, String> conditionMap = new HashMap<>(16);
                conditionMap.put(GenerateSqlUtil.SCHEMA_TABLE + GenerateSqlUtil.DEFAULT_NUM, tableName);
                String generateSql = GenerateSqlUtil.generateSql(tempSql, conditionMap, wherePartSql);
                sqlBuffer.append(generateSql);
                if (index.get() != tableList.size() - 1) {
                    sqlBuffer.append(GenerateSqlUtil.UNION_ALL);
                }
                index.getAndIncrement();
            }
        } else {
            throw new BizException("规则信息生成SQL,规则类型为: " + dqcSchedulerRulesVO.getRuleType() + ",表为空!");
        }
    }

    /**
     * 生成字段级规则sql
     *
     * @param dqcSchedulerRulesVO
     * @param sqlBuffer
     * @param tempSql
     */
    private void ruleTypeFieldSql(DqcSchedulerRulesVO dqcSchedulerRulesVO, StringBuilder sqlBuffer, String tempSql) {
        //表信息
        List<String> tableList = dqcSchedulerRulesVO.getTableList();
        if (tableList != null) {
            String tableName = tableList.get(0);
            //字段信息
            List<String> fieldList = dqcSchedulerRulesVO.getFieldList();
            //扫描方式(全部/条件)
            String wherePartSql = getScanSql(dqcSchedulerRulesVO);

            AtomicInteger index = new AtomicInteger(0);
            if (fieldList != null) {
                for (String field : fieldList) {
                    //元数据查询参数
                    Map<String, String> conditionMap = new HashMap<>(16);
                    conditionMap.put(GenerateSqlUtil.SCHEMA_TABLE + GenerateSqlUtil.DEFAULT_NUM, tableName);
                    conditionMap.put(GenerateSqlUtil.COL + GenerateSqlUtil.DEFAULT_NUM, field);
                    //生成执行sql
                    String generateSql = GenerateSqlUtil.generateSql(tempSql, conditionMap, wherePartSql);
                    sqlBuffer.append(generateSql);
                    if (index.get() != fieldList.size() - 1) {
                        sqlBuffer.append(GenerateSqlUtil.UNION_ALL);
                    }
                    index.getAndIncrement();
                }
            } else {
                //TODO 直接读取使用模板SQL,暂时不支持扫描条件SQL
                sqlBuffer.append(tempSql);
            }
        } else {
            //TODO 直接读取使用模板SQL,暂时不支持扫描条件SQL
            sqlBuffer.append(tempSql);
        }
    }

    /**
     * 扫描方式(全部/条件)
     *
     * @param dqcSchedulerRulesVO
     * @return String
     */
    private String getScanSql(DqcSchedulerRulesVO dqcSchedulerRulesVO) {
        StringBuilder wherePartSqlBuilder = new StringBuilder();
        String scanType = dqcSchedulerRulesVO.getScanType();
        //and day = tradeDay1(20211215,1) and day2 = tradeDay2(20211215,2)
        String scanSql = dqcSchedulerRulesVO.getScanSql();

        if (ScanTypeEnum.FULL_TABLE.getCode().equalsIgnoreCase(scanType)) {
            //全表
            wherePartSqlBuilder.append(GenerateSqlUtil.WHERE_PART);
        } else if (ScanTypeEnum.CONDITION.getCode().equalsIgnoreCase(scanType)) {
            //条件
            wherePartSqlBuilder.append(GenerateSqlUtil.WHERE_PART);
            wherePartSqlBuilder.append(GenerateSqlUtil.AND);
            //执行Groovy函数生成sql片段
            wherePartSqlBuilder.append(scanSqlByGroovyFunction(scanSql));
        }

        return wherePartSqlBuilder.toString();
    }

    /**
     * 执行Groovy函数生成sql片段
     *
     * @param scanSql
     * @return String
     */
    private String scanSqlByGroovyFunction(String scanSql) {
        String realScanSql = "";
        //是否存在函数
        Map<String, RuleFunctionInfo> existFunctionMap = ruleFunctionModelBuilder.isExistFunction(scanSql);

        if (existFunctionMap.size() > 0) {
            //执行Groovy函数
//            Map<String, Object> groovyFunctionMap = executeGroovyFunction(existFunctionMap);
            //替换函数sql参数
//            realScanSql = getRealScanSql(scanSql, realScanSql, functionNameList, groovyFunctionMap);
        } else {
            //存在特殊函数
            realScanSql = scanSql;
        }
        return realScanSql;
    }

    /**
     * 真正需要被执行的函数带有参数信息等
     */
    private List<String> getFunctionNameList(String scanSql, List<String> functions) {
        List<String> functionNameList = new ArrayList<>();
        //and
        String trimStartScanSql = GenerateSqlUtil.trimStart(scanSql);
        String[] andPartArr = trimStartScanSql.split(GenerateSqlUtil.AND);
        //获取需要执行函数的sql片段
        for (String sqlPart : andPartArr) {
            String functionName = "";
            for (String function : functions) {
                if (sqlPart.contains(function)) {
                    String[] sqlPartArr = sqlPart.split(function);
                    functionName = function + sqlPartArr[sqlPartArr.length - 1];
                }
            }
            //Groovy函数名称
            functionNameList.add(functionName);
        }
        return functionNameList;
    }

//    /**
//     * 执行Groovy函数
//     */
//    private Map<String, Object> executeGroovyFunction(Map<String, RuleFunctionInfo> existFunctionMap) {
//        FactModel model = new FactModel();
//        List<RuleFunctionInfo> ruleFunctionInfos = Lists.newArrayList();
//
//        for (String functionName : functionNameList) {
//            RuleFunctionInfo ruleFunctionInfo = new RuleFunctionInfo("tradeDay", "tradeDay", "String", LocalDateTime.now());
//            ruleFunctionInfo.setExpression("tradeDay('20220106')");
//            ruleFunctionInfos.add(ruleFunctionInfo);
//        }
//        model.setRuleFunctionInfo(ruleFunctionInfos);
//        RuleFunctionGenerator ruleFunctionGenerator = new RuleFunctionGenerator(model);
//        return ruleFunctionGenerator.generateFunction();
//    }

    /**
     * 替换函数sql参数
     */
    private String getRealScanSql(String scanSql, String realScanSql, List<String> functionNameList, Map<String, Object> groovyFunctionMap) {
        for (String functionName : functionNameList) {
            Map<String, String> groovySqlResultMap = (Map<String, String>) groovyFunctionMap.get(FunctionConstant.RULE_FUNCTION_TYPE);
            String groovySql = groovySqlResultMap.get(functionName);
            if (!ObjectUtils.isEmpty(groovySql)) {
                realScanSql = scanSql.replace(functionName, groovySql);
            } else {
                realScanSql = scanSql;
            }
        }
        return realScanSql;
    }

}
