package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据质量_规则调度_基础信息
 *
 * @author wjq
 * @date 2021/11/08
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/scheduler/basic/info")
public class DqcSchedulerBasicInfoController {
  private final DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService;

  @Autowired
  public DqcSchedulerBasicInfoController(
      DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService) {
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
  public DefaultCommonResult<PageResultVO<DqcSchedulerBasicInfoVO>> searchPageList(
      @RequestBody DqcSchedulerInfoParamsVO dsdSchedulerAllParamsVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dqcSchedulerBasicInfoService.searchPageList(dsdSchedulerAllParamsVO));
  }

  /**
   * 新增规则调度_基础信息
   *
   * @param dqcSchedulerBasicInfoVO
   * @return DefaultCommonResult
   */
  @PostMapping("")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
  public DefaultCommonResult<String> insert(
      @RequestBody @Validated DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dqcSchedulerBasicInfoService.insert(dqcSchedulerBasicInfoVO));
  }

  /**
   * 编辑规则调度_基础信息
   *
   * @param dqcSchedulerBasicInfoVO
   * @return DefaultCommonResult
   */
  @PutMapping("")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
  public DefaultCommonResult update(
      @RequestBody @Validated DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
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
  public DefaultCommonResult deleteOne(@PathVariable("id") Long id) {
    dqcSchedulerBasicInfoService.deleteOne(id);
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

  /**
   * 操作(启动、停止、运行)
   *
   * @param dqcSchedulerBasicInfoVO
   * @return
   */
  @PutMapping("/execute")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
  public DefaultCommonResult execute(
          @RequestBody DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
    dqcSchedulerBasicInfoService.execute(dqcSchedulerBasicInfoVO);
    return DefaultCommonResult.success();
  }

  @GetMapping("/instance/{id}")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
  public DefaultCommonResult instanceDetail(@PathVariable("id") Integer id) {
    return DefaultCommonResult.success(
            ResultCodeEnum.OK, dqcSchedulerBasicInfoService.instanceDetail(id));
  }

  @GetMapping("/definition/{id}/instance")
//    @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
  public DefaultCommonResult instanceDetailByList(@PathVariable("id") Integer id) {
    return DefaultCommonResult.success(
            ResultCodeEnum.OK, dqcSchedulerBasicInfoService.instanceDetailByList(id));
  }
}
