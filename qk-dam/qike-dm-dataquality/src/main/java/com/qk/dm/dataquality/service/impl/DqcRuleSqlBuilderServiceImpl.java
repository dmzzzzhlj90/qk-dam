package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.RuleTypeEnum;
import com.qk.dm.dataquality.constant.ScanTypeEnum;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import com.qk.dm.dataquality.entity.QDqcRuleTemplate;
import com.qk.dm.dataquality.entity.QDqcSchedulerRules;
import com.qk.dm.dataquality.groovy.RuleFunctionModelComponent;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerRulesMapper;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerRulesRepository;
import com.qk.dm.dataquality.service.DqcRuleSqlBuilderService;
import com.qk.dm.dataquality.utils.GenerateSqlUtil;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private final RuleFunctionModelComponent ruleFunctionModelComponent;


    @Autowired
    public DqcRuleSqlBuilderServiceImpl(DqcRuleTemplateRepository dqcRuleTemplateRepository,
                                        DqcSchedulerRulesRepository dqcSchedulerRulesRepository,
                                        RuleFunctionModelComponent ruleFunctionModelComponent) {
        this.dqcRuleTemplateRepository = dqcRuleTemplateRepository;
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.ruleFunctionModelComponent = ruleFunctionModelComponent;
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
        if (tableList != null && tableList.size() > 0) {
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
            wherePartSqlBuilder.append(ruleFunctionModelComponent.scanSqlByGroovyFunction(scanSql));
        }
        return wherePartSqlBuilder.toString();
    }

}
