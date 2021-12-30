//package com.qk.dm.dataquality.rest;
//
//import com.qk.dam.commons.enums.ResultCodeEnum;
//import com.qk.dam.commons.http.result.DefaultCommonResult;
//import com.qk.dam.jpa.pojo.PageResultVO;
//import com.qk.dm.client.DataBaseInfoDefaultApi;
//import com.qk.dm.dataquality.service.DqcSchedulerRulesService;
//import com.qk.dm.dataquality.vo.DqcSchedulerRulesParamsVO;
//import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.com.qk.dm.groovy.util.List;
//
//
///**
// * 数据质量_规则调度_规则信息
// *
// * @author wjq
// * @date 2021/11/08
// * @since 1.0.0
// */
//@Slf4j
//@RestController
//@RequestMapping("/scheduler/rules")
//public class DqcSchedulerRulesController {
//    private final DqcSchedulerRulesService dqcSchedulerRulesService;
//    private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;
//
//
//    @Autowired
//    public DqcSchedulerRulesController(DqcSchedulerRulesService dqcSchedulerRulesService, DataBaseInfoDefaultApi dataBaseInfoDefaultApi) {
//        this.dqcSchedulerRulesService = dqcSchedulerRulesService;
//        this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
//    }
//
//    /**
//     * 获取规则调度_规则信息列表
//     *
//     * @param dqcSchedulerRulesParamsVO
//     * @return DefaultCommonResult<PageResultVO < DqcSchedulerRulesVO>>
//     */
//    @PostMapping("/page/list")
//    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
//    public DefaultCommonResult<PageResultVO<DqcSchedulerRulesVO>> searchPageList(@RequestBody DqcSchedulerRulesParamsVO dqcSchedulerRulesParamsVO) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerRulesService.searchPageList(dqcSchedulerRulesParamsVO));
//    }
//
//    /**
//     * 新增规则调度_规则信息
//     *
//     * @param dqcSchedulerRulesVO
//     * @return DefaultCommonResult
//     */
//    @PostMapping("")
//    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
//    public DefaultCommonResult insert(@RequestBody @Validated DqcSchedulerRulesVO dqcSchedulerRulesVO) {
//        dqcSchedulerRulesService.insert(dqcSchedulerRulesVO);
//        return DefaultCommonResult.success();
//    }
//
//    /**
//     * 编辑规则调度_规则信息
//     *
//     * @param dqcSchedulerRulesVO
//     * @return DefaultCommonResult
//     */
//    @PutMapping("")
//    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
//    public DefaultCommonResult update(@RequestBody @Validated DqcSchedulerRulesVO dqcSchedulerRulesVO) {
//        dqcSchedulerRulesService.update(dqcSchedulerRulesVO);
//        return DefaultCommonResult.success();
//    }
//
//    /**
//     * 删除单个规则调度_规则信息
//     *
//     * @param id
//     * @return DefaultCommonResult
//     */
//    @DeleteMapping("/{id}")
//    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
//    public DefaultCommonResult deleteOne(@PathVariable("id") String id) {
//        dqcSchedulerRulesService.deleteOne(Long.valueOf(id));
//        return DefaultCommonResult.success();
//    }
//
//    /**
//     * 批量删除单个规则调度_规则信息
//     *
//     * @param ids
//     * @return DefaultCommonResult
//     */
//    @DeleteMapping("/root/{ids}")
//    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
//    public DefaultCommonResult deleteBulk(@PathVariable("ids") String ids) {
//        dqcSchedulerRulesService.deleteBulk(ids);
//        return DefaultCommonResult.success();
//    }
//
//}
