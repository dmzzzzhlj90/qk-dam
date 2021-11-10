package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.vo.DqcRuleTemplateListVo;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DsdSchedulerAllParamsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据质量_规则调度_基础信息
 *
 * @author wjq
 * @date 20211108
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/scheduler/basic/info")
public class DqcSchedulerBasicInfoController {
    private final DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService;

    @Autowired
    public DqcSchedulerBasicInfoController(DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService) {
        this.dqcSchedulerBasicInfoService = dqcSchedulerBasicInfoService;
    }

    /**
     * 获取规则调度_基础信息列表
     *
     * @param dsdSchedulerAllParamsVO
     * @return DefaultCommonResult<PageResultVO<DqcSchedulerBasicInfoVO>>
     */
    @GetMapping("/page/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DqcSchedulerBasicInfoVO>> searchPageList(@RequestBody DsdSchedulerAllParamsVO dsdSchedulerAllParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerBasicInfoService.searchPageList(dsdSchedulerAllParamsVO));
    }

    /**
     * 新增规则调度_基础信息
     *
     * @param dqcSchedulerBasicInfoVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody @Validated DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        dqcSchedulerBasicInfoService.insert(dqcSchedulerBasicInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑规则调度_基础信息
     *
     * @param dqcSchedulerBasicInfoVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody @Validated DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        dqcSchedulerBasicInfoService.update(dqcSchedulerBasicInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 删除单个规则调度_基础信息
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{id}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult delete(@PathVariable("id") String id) {
        dqcSchedulerBasicInfoService.delete(Long.valueOf(id));
        return DefaultCommonResult.success();
    }

    /**
     * 批量删除单个规则调度_基础信息
     *
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/root/{ids}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@PathVariable("ids") String ids) {
        dqcSchedulerBasicInfoService.deleteBulk(ids);
        return DefaultCommonResult.success();
    }
}
