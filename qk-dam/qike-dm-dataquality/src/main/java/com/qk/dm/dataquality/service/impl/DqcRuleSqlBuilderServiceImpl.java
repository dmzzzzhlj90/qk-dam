package com.qk.dm.dataquality.service.impl;

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
        //todo 获取模板SQL;需要考虑自定义模式的模板信息
        Optional<DqcRuleTemplate> dqcRuleTemplate = dqcRuleTemplateRepository.findOne(QDqcRuleTemplate.dqcRuleTemplate.id.eq(dqcSchedulerRulesVO.getRuleTempId()));

        StringBuilder sqlBuffer = new StringBuilder();
        if (dqcRuleTemplate.isPresent()) {
            //解析替换参数信息
            buildExecuteSql(dqcSchedulerRulesVO, dqcRuleTemplate, sqlBuffer);
        }
        return sqlBuffer.toString();
    }

    private void buildExecuteSql(DqcSchedulerRulesVO dqcSchedulerRulesVO, Optional<DqcRuleTemplate> dqcRuleTemplate, StringBuilder sqlBuffer) {
        String ruleType = dqcSchedulerRulesVO.getRuleType();
        String tempSql = dqcRuleTemplate.get().getTempSql();
        if (RuleTypeEnum.RULE_TYPE_FIELD.getCode().equalsIgnoreCase(ruleType)) {
            //TODO 字段级别规则 需要考虑多个规则情况参数替换问题
            String tableName = dqcSchedulerRulesVO.getTableName();
            List<String> fieldList = dqcSchedulerRulesVO.getFieldList();
            AtomicInteger index = new AtomicInteger(0);
            for (String field : fieldList) {
                Map<String, String> conditionMap = new HashMap<>(16);
                conditionMap.put(GenerateSqlUtil.SCHEMA_TABLE + GenerateSqlUtil.DEFAULT_NUM, tableName);
                conditionMap.put(GenerateSqlUtil.COL + GenerateSqlUtil.DEFAULT_NUM, field);
                String generateSql = GenerateSqlUtil.generateSql(tempSql, conditionMap);
                sqlBuffer.append(generateSql);
                if (index.get() != fieldList.size() - 1) {
                    sqlBuffer.append(" UNION ALL ");
                }
                index.getAndIncrement();
            }
        }
    }

}
