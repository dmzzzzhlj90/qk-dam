package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.service.DataServiceApiBasicInfoService;
import com.qk.dm.dataservice.vo.DasApiBasicInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiBasicinfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据服务_API基础信息
 *
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/basic/info")
public class DataServiceApiBasicInfoController {
  private final DataServiceApiBasicInfoService dataServiceApiBasicInfoService;

  @Autowired
  public DataServiceApiBasicInfoController(
      DataServiceApiBasicInfoService dataServiceApiBasicInfoService) {
    this.dataServiceApiBasicInfoService = dataServiceApiBasicInfoService;
  }

  /**
   * 统一查询API基础信息入口
   *
   * @param dasApiBasicInfoParamsVO
   * @return DefaultCommonResult<PageResultVO < DasApiBasicInfoVO>>
   */
  @PostMapping(value = "/query")
  public DefaultCommonResult<PageResultVO<DasApiBasicinfoVO>> getDasApiBasicInfoByDsdLevelId(
      @RequestBody DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK,
        dataServiceApiBasicInfoService.getDasApiBasicInfo(dasApiBasicInfoParamsVO));
  }

  /**
   * 新增API基础信息
   *
   * @param dasApiBasicInfoVO
   * @return DefaultCommonResult
   */
  @PostMapping("/add")
  public DefaultCommonResult addDasApiBasicInfo(
      @RequestBody @Validated DasApiBasicinfoVO dasApiBasicInfoVO) {
    dataServiceApiBasicInfoService.addDasApiBasicInfo(dasApiBasicInfoVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑API基础信息
   *
   * @param dasApiBasicInfoVO
   * @return DefaultCommonResult
   */
  @PutMapping("/update")
  public DefaultCommonResult updateDasApiBasicInfo(
      @RequestBody @Validated DasApiBasicinfoVO dasApiBasicInfoVO) {
    dataServiceApiBasicInfoService.updateDasApiBasicInfo(dasApiBasicInfoVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除API基础信息
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/delete/{id}")
  public DefaultCommonResult deleteDasApiBasicInfo(@PathVariable("id") String id) {
    dataServiceApiBasicInfoService.deleteDasApiBasicInfo(Long.valueOf(id));
    return DefaultCommonResult.success();
  }

  /**
   * 批量删除API基础信息
   *
   * @param ids
   * @return DefaultCommonResult
   */
  @DeleteMapping("/bulk/delete/{ids}")
  public DefaultCommonResult bulkDeleteDasApiBasicInfo(@PathVariable("ids") String ids) {
    dataServiceApiBasicInfoService.bulkDeleteDasApiBasicInfo(ids);
    return DefaultCommonResult.success();
  }
}
