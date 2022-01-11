package com.qk.dm.dataquality.biz;

import com.qk.dm.dataquality.service.DqcRuleTemplateService;
import com.qk.dm.dataquality.vo.statistics.RuleTemplateVO;
import org.springframework.stereotype.Component;

/**
 * @author shenpj
 * @date 2021/12/23 8:02 下午
 * @since 1.0.0
 */
@Component
public class RuleTemplateBiz {
    private DqcRuleTemplateService dqcRuleTemplateService;

    public RuleTemplateBiz(DqcRuleTemplateService dqcRuleTemplateService) {
        this.dqcRuleTemplateService = dqcRuleTemplateService;
    }

    public RuleTemplateVO ruleTemplateStatistics() {
        return RuleTemplateVO.builder()
                //规则模版总数
                .count(dqcRuleTemplateService.getCount())
                //规则模版内置模版数
                .systemCount(dqcRuleTemplateService.getSystemCount())
                //规则模版自定义模版数
                .customCount(dqcRuleTemplateService.getCustomCount())
                .build();
    }
}
