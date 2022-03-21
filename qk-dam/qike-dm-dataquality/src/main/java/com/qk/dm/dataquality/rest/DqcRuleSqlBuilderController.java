package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.service.DqcRuleSqlBuilderService;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 生成执行sql
 *
 * @author wjq
 * @date 2021/12/04
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/sql/build/")
public class DqcRuleSqlBuilderController {
    private final DqcRuleSqlBuilderService dqcRuleSqlBuilderService;

    @Autowired
    public DqcRuleSqlBuilderController(DqcRuleSqlBuilderService dqcRuleSqlBuilderService) {
        this.dqcRuleSqlBuilderService = dqcRuleSqlBuilderService;
    }

    /**
     * 根据规则模板获取执行Sql
     *
     * @param dqcSchedulerRulesVO
     * @return DefaultCommonResult
     */
    @PostMapping("/execute")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult getExecuteSql(@RequestBody DqcSchedulerRulesVO dqcSchedulerRulesVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcRuleSqlBuilderService.getExecuteSql(dqcSchedulerRulesVO));
    }

    /**
     * 动态实时获取执行sql(python执行脚本回调接口)
     *
     * @param ruleId
     * @return DefaultCommonResult
     */
    @GetMapping("/realtime")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult getRealTimeSql(@RequestParam("ruleId") String ruleId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcRuleSqlBuilderService.getRealTimeSql(ruleId));
    }

}