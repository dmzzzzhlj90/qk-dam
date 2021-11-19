package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.service.DqcSchedulerInfoService;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import com.qk.dm.dataquality.vo.SchedulerRuleConstantsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据质量_规则调度信息入口
 *
 * @author wjq
 * @date 2021/11/08
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/scheduler/info/")
public class DqcSchedulerInfoController {
    private final DqcSchedulerInfoService dqcSchedulerInfoService;

    @Autowired
    public DqcSchedulerInfoController(DqcSchedulerInfoService dqcSchedulerInfoService) {
        this.dqcSchedulerInfoService = dqcSchedulerInfoService;
    }

    /**
     * 获取规则调度_规则调度列表信息
     *
     * @param schedulerInfoParamsVO
     * @return DefaultCommonResult<PageResultVO < DqcSchedulerRulesVO>>
     */
    @PostMapping("/page/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DqcSchedulerInfoVO>> searchPageList(@RequestBody DqcSchedulerInfoParamsVO schedulerInfoParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerInfoService.searchPageList(schedulerInfoParamsVO));
    }

    /**
     * 新增规则调度信息(基础信息,规则,调度配置)
     *
     * @param dqcSchedulerInfoVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult insert(@RequestBody DqcSchedulerInfoVO dqcSchedulerInfoVO) {
        dqcSchedulerInfoService.insert(dqcSchedulerInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑规则调度信息(基础信息,规则,调度配置)
     *
     * @param dqcSchedulerInfoVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult update(@RequestBody DqcSchedulerInfoVO dqcSchedulerInfoVO) {
        dqcSchedulerInfoService.update(dqcSchedulerInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * //TODO 需要关联删除
     * 删除单个规则调度_规则调度信息
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{id}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteOne(@PathVariable("id") String id) {
        dqcSchedulerInfoService.deleteOne(Long.valueOf(id));
        return DefaultCommonResult.success();
    }

    /**
     * //TODO 需要关联删除
     * 批量删除单个规则调度_规则调度
     *
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/root/{ids}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@PathVariable("ids") String ids) {
        dqcSchedulerInfoService.deleteBulk(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 获取调度规则常量信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/scheduler/rule/constants")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult<SchedulerRuleConstantsVO> getSchedulerRuLeConstants() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerInfoService.getSchedulerRuLeConstants());
    }
}
