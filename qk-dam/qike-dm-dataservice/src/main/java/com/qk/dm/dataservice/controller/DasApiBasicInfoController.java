package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.vo.DasApiBasicInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import java.util.List;
import java.util.Map;
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
public class DasApiBasicInfoController {
  private final DasApiBasicInfoService dasApiBasicInfoService;

  @Autowired
  public DasApiBasicInfoController(DasApiBasicInfoService dasApiBasicInfoService) {
    this.dasApiBasicInfoService = dasApiBasicInfoService;
  }

  /**
   * 统一查询API基础信息入口
   *
   * @param dasApiBasicInfoParamsVO
   * @return DefaultCommonResult<PageResultVO < DasApiBasicInfoVO>>
   */
  @PostMapping(value = "/query")
  public DefaultCommonResult<PageResultVO<DasApiBasicInfoVO>> getDasApiBasicInfoByDsdLevelId(
      @RequestBody DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiBasicInfoService.getDasApiBasicInfo(dasApiBasicInfoParamsVO));
  }

  /**
   * 新增API基础信息
   *
   * @param dasApiBasicInfoVO
   * @return DefaultCommonResult
   */
  @PostMapping("/add")
  public DefaultCommonResult addDasApiBasicInfo(
      @RequestBody @Validated DasApiBasicInfoVO dasApiBasicInfoVO) {
    dasApiBasicInfoService.addDasApiBasicInfo(dasApiBasicInfoVO);
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
      @RequestBody @Validated DasApiBasicInfoVO dasApiBasicInfoVO) {
    dasApiBasicInfoService.updateDasApiBasicInfo(dasApiBasicInfoVO);
    return DefaultCommonResult.success();
  }

  /**
   * //TODO 删除API,根据API类型对应删除,统一放到删除基础信息进行删除 删除API基础信息
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/delete/{id}")
  public DefaultCommonResult deleteDasApiBasicInfo(@PathVariable("id") String id) {
    dasApiBasicInfoService.deleteDasApiBasicInfo(Long.valueOf(id));
    return DefaultCommonResult.success();
  }

  /**
   * //TODO 删除API,根据API类型对应删除,统一放到删除基础信息进行删除 批量删除API基础信息
   *
   * @param ids
   * @return DefaultCommonResult
   */
  @DeleteMapping("/bulk/delete/{ids}")
  public DefaultCommonResult bulkDeleteDasApiBasicInfo(@PathVariable("ids") String ids) {
    dasApiBasicInfoService.bulkDeleteDasApiBasicInfo(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 获取API类型
   *
   * @return DefaultCommonResult<PageResultVO < DasApiBasicInfoVO>>
   */
  @GetMapping(value = "/query/api/type")
  public DefaultCommonResult<List<Map<String, String>>> getApiType() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiBasicInfoService.getApiType());
  }

  /**
   * 获取取数方式
   *
   * @return DefaultCommonResult<PageResultVO < DasApiBasicInfoVO>>
   */
  @GetMapping(value = "/query/dm/source/type")
  public DefaultCommonResult<List<Map<String, String>>> getDMSourceType() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiBasicInfoService.getDMSourceType());
  }

  /**
   * API基础信息__入参定义表头
   *
   * @return DefaultCommonResult<Map<String, String>>
   */
  @GetMapping(value = "/query/request/paras/header/infos")
  public DefaultCommonResult<Map<String, String>> getRequestParasHeaderInfos() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiBasicInfoService.getRequestParasHeaderInfos());
  }

  /**
   * API基础信息__参数位置下拉列表
   *
   * @return DefaultCommonResult<Map<String, String>>
   */
  @GetMapping(value = "/query/request/param/positions")
  public DefaultCommonResult<Map<String, String>> getRequestParamsPositions() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiBasicInfoService.getRequestParamsPositions());
  }
}
