package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.service.DqcRuleSqlBuilderService;
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
     * @param
     * @return DefaultCommonResult
     */
    @GetMapping("/execute")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult getExecuteSql(@RequestParam("tempSQL") String tempSQL,
                                             @RequestParam("condition") String condition,
                                             @RequestParam("value") String value) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcRuleSqlBuilderService.getExecuteSql(tempSQL, condition, value));
    }

}
