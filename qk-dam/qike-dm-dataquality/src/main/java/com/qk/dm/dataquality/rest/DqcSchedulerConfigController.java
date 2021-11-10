package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import com.qk.dm.dataquality.vo.DsdSchedulerAllParamsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据质量_规则调度_配置信息
 *
 * @author wjq
 * @date 20211108
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/scheduler/config/")
public class DqcSchedulerConfigController {
    private final DqcSchedulerConfigService dqcSchedulerConfigService;

    @Autowired
    public DqcSchedulerConfigController(DqcSchedulerConfigService dqcSchedulerConfigService) {
        this.dqcSchedulerConfigService = dqcSchedulerConfigService;
    }
    
    /**
     * 获取规则调度_配置信息列表
     *
     * @param dsdSchedulerAllParamsVO
     * @return DefaultCommonResult<PageResultVO<DqcSchedulerConfigVO>>
     */
    @GetMapping("/page/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DqcSchedulerConfigVO>> searchPageList(@RequestBody DsdSchedulerAllParamsVO dsdSchedulerAllParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerConfigService.searchPageList(dsdSchedulerAllParamsVO));
    }

    /**
     * 新增规则调度_配置信息
     *
     * @param dqcSchedulerRulesVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody @Validated DqcSchedulerRulesVO dqcSchedulerRulesVO) {
        dqcSchedulerConfigService.insert(dqcSchedulerRulesVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑规则调度_配置信息
     *
     * @param dqcSchedulerRulesVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody @Validated DqcSchedulerRulesVO dqcSchedulerRulesVO) {
        dqcSchedulerConfigService.update(dqcSchedulerRulesVO);
        return DefaultCommonResult.success();
    }

    /**
     * 删除单个规则调度_配置信息
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{id}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult delete(@PathVariable("id") String id) {
        dqcSchedulerConfigService.delete(Long.valueOf(id));
        return DefaultCommonResult.success();
    }

    /**
     * 批量删除单个规则调度_配置信息
     *
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/root/{ids}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@PathVariable("ids") String ids) {
        dqcSchedulerConfigService.deleteBulk(ids);
        return DefaultCommonResult.success();
    }

}
