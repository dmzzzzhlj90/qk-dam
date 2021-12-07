package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataquality.constant.RuleTypeEnum;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.entity.QDqcRuleTemplate;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.service.DqcRuleSqlBuilderService;
import com.qk.dm.dataquality.utils.GenerateSqlUtil;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Autowired
    public DqcRuleSqlBuilderServiceImpl(DqcRuleTemplateRepository dqcRuleTemplateRepository) {
        this.dqcRuleTemplateRepository = dqcRuleTemplateRepository;
    }


    @Override
    public String getExecuteSql(DqcSchedulerRulesVO dqcSchedulerRulesVO) {
        Optional<DqcRuleTemplate> dqcRuleTemplate = dqcRuleTemplateRepository.findOne(QDqcRuleTemplate.dqcRuleTemplate.id.eq(dqcSchedulerRulesVO.getRuleTempId()));

        StringBuilder sqlBuffer = new StringBuilder();
        //解析替换参数信息
        dqcRuleTemplate.ifPresent(ruleTemplate -> buildExecuteSql(dqcSchedulerRulesVO, ruleTemplate, sqlBuffer));
        return sqlBuffer.toString();
    }

    private void buildExecuteSql(DqcSchedulerRulesVO dqcSchedulerRulesVO, DqcRuleTemplate dqcRuleTemplate, StringBuilder sqlBuffer) {
        String ruleType = dqcSchedulerRulesVO.getRuleType();
        String tempSql = dqcRuleTemplate.getTempSql();

        //库级别规则
        if (RuleTypeEnum.RULE_TYPE_DB.getCode().equalsIgnoreCase(ruleType)) {
            ruleTypeDB(dqcSchedulerRulesVO, sqlBuffer, tempSql);
        }

        //表级别规则
        if (RuleTypeEnum.RULE_TYPE_TABLE.getCode().equalsIgnoreCase(ruleType)) {
            ruleTypeTable(dqcSchedulerRulesVO, sqlBuffer, tempSql);
        }

        //字段级别规则
        if (RuleTypeEnum.RULE_TYPE_FIELD.getCode().equalsIgnoreCase(ruleType)) {
            ruleTypeField(dqcSchedulerRulesVO, sqlBuffer, tempSql);
        }

        //TODO 获取模板SQL;需要考虑自定义模式的模板信息


    }

    private void ruleTypeDB(DqcSchedulerRulesVO dqcSchedulerRulesVO, StringBuilder sqlBuffer, String tempSql) {
        ruleTypeTable(dqcSchedulerRulesVO, sqlBuffer, tempSql);
    }

    private void ruleTypeTable(DqcSchedulerRulesVO dqcSchedulerRulesVO, StringBuilder sqlBuffer, String tempSql) {
        List<String> tableList = dqcSchedulerRulesVO.getTableList();
        if (tableList != null) {
            AtomicInteger index = new AtomicInteger(0);
            for (String tableName : tableList) {
                Map<String, String> conditionMap = new HashMap<>(16);
                conditionMap.put(GenerateSqlUtil.SCHEMA_TABLE + GenerateSqlUtil.DEFAULT_NUM, tableName);
                String generateSql = GenerateSqlUtil.generateSql(tempSql, conditionMap);
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

    private void ruleTypeField(DqcSchedulerRulesVO dqcSchedulerRulesVO, StringBuilder sqlBuffer, String tempSql) {
        List<String> tableList = dqcSchedulerRulesVO.getTableList();
        String tableName = tableList.get(0);
        List<String> fieldList = dqcSchedulerRulesVO.getFieldList();
        AtomicInteger index = new AtomicInteger(0);

        if (fieldList != null) {
            for (String field : fieldList) {
                Map<String, String> conditionMap = new HashMap<>(16);
                conditionMap.put(GenerateSqlUtil.SCHEMA_TABLE + GenerateSqlUtil.DEFAULT_NUM, tableName);
                conditionMap.put(GenerateSqlUtil.COL + GenerateSqlUtil.DEFAULT_NUM, field);
                String generateSql = GenerateSqlUtil.generateSql(tempSql, conditionMap);
                sqlBuffer.append(generateSql);
                if (index.get() != fieldList.size() - 1) {
                    sqlBuffer.append(GenerateSqlUtil.UNION_ALL);
                }
                index.getAndIncrement();
            }
        } else {
            throw new BizException("规则信息生成SQL,规则类型为: " + dqcSchedulerRulesVO.getRuleType() + ",字段为空!");
        }
    }

}
