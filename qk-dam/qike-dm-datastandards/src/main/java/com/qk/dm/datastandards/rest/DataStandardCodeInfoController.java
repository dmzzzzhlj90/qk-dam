package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datastandards.service.DataStandardCodeInfoService;
import com.qk.dm.datastandards.vo.*;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据标准__码表管理接口
 *
 * @author wjq
 * @date 20210726
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/code/info")
public class DataStandardCodeInfoController {
  private final DataStandardCodeInfoService dataStandardCodeInfoService;

  @Autowired
  public DataStandardCodeInfoController(DataStandardCodeInfoService dataStandardCodeInfoService) {
    this.dataStandardCodeInfoService = dataStandardCodeInfoService;
  }

  /**
   * 高级筛选_查询码表基础信息列表
   *
   * @param dsdCodeInfoParamsVO
   * @return DefaultCommonResult<PageResultVO < DsdCodeInfoVO>>
   */
  @PostMapping(value = "/basic/query")
  public DefaultCommonResult<PageResultVO<DsdCodeInfoVO>> getDsdCodeInfo(
      @RequestBody DsdCodeInfoParamsVO dsdCodeInfoParamsVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dataStandardCodeInfoService.getDsdCodeInfo(dsdCodeInfoParamsVO));
  }

  /**
   * 新增码表基础信息
   *
   * @param dsdCodeInfoVO
   * @return DefaultCommonResult
   */
  @PostMapping("/basic/add")
  public DefaultCommonResult addDsdCodeInfo(@RequestBody @Validated DsdCodeInfoVO dsdCodeInfoVO) {
    dataStandardCodeInfoService.addDsdCodeInfo(dsdCodeInfoVO);
    return DefaultCommonResult.success();
  }

  /**
   * 根据Id获取码表信息列表详情信息
   *
   * @param id
   * @return DefaultCommonResult<DsdCodeInfoVO>
   */
  @GetMapping(value = "/basic/query/by/{id}")
  public DefaultCommonResult<DsdCodeInfoVO> getBasicDsdCodeInfoById(@PathVariable("id") String id) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK,
        dataStandardCodeInfoService.getDsdCodeInfoById(Long.valueOf(id).longValue()));
  }

  /**
   * 编辑码表信息
   *
   * @param dsdCodeInfoVO
   * @return DefaultCommonResult
   */
  @PutMapping("/basic/update")
  public DefaultCommonResult modifyDsdCodeInfo(
      @RequestBody @Validated DsdCodeInfoVO dsdCodeInfoVO) {
    dataStandardCodeInfoService.modifyDsdCodeInfo(dsdCodeInfoVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除码表信息
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/basic/delete/{id}")
  public DefaultCommonResult deleteDsdCodeInfo(@PathVariable("id") Integer id) {
    dataStandardCodeInfoService.deleteDsdCodeInfo(Long.valueOf(id).longValue());
    return DefaultCommonResult.success();
  }

  /**
   * 批量删除码表信息
   *
   * @param ids
   * @return DefaultCommonResult
   */
  @DeleteMapping("/basic/delete/bulk/{ids}")
  public DefaultCommonResult deleteBulkDsdCodeInfo(@PathVariable("ids") String ids) {
    dataStandardCodeInfoService.deleteBulkDsdCodeInfo(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 数据类型下拉列表
   *
   * @return0 DefaultCommonResult
   */
  @GetMapping("/data/types")
  public DefaultCommonResult<List<Map<String, String>>> getDataTypes() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dataStandardCodeInfoService.getDataTypes());
  }

  /**
   * 高级筛选_查询码表数值以及扩展信息列表
   *
   * @param dsdCodeInfoExtParamsVO
   * @return DefaultCommonResult<Map < String, Object>>
   */
  @PostMapping(value = "/ext/query")
  public DefaultCommonResult<Map<String, Object>> getDsdCodeInfoExt(
      @RequestBody DsdCodeInfoExtParamsVO dsdCodeInfoExtParamsVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dataStandardCodeInfoService.getDsdCodeInfoExt(dsdCodeInfoExtParamsVO));
  }

  /**
   * 新增码表数值信息
   *
   * @param dsdCodeInfoExtVO
   * @return DefaultCommonResult
   */
  @PostMapping("/ext/add")
  public DefaultCommonResult addDsdCodeInfoExt(
      @RequestBody @Validated DsdCodeInfoExtVO dsdCodeInfoExtVO) {
    dataStandardCodeInfoService.addDsdCodeInfoExt(dsdCodeInfoExtVO);
    return DefaultCommonResult.success();
  }

  /**
   * 根据Id获取码表数值列表详情信息
   *
   * @param id
   * @return DefaultCommonResult<DsdCodeInfoExtVO>
   */
  @GetMapping(value = "/ext/query/by/{id}")
  public DefaultCommonResult<DsdCodeInfoExtVO> getBasicDsdCodeInfoExtById(
      @PathVariable("id") String id) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK,
        dataStandardCodeInfoService.getBasicDsdCodeInfoExtById(Long.valueOf(id).longValue()));
  }

  /**
   * 编辑码表数值信息
   *
   * @param dsdCodeInfoExtVO
   * @return DefaultCommonResult
   */
  @PutMapping("/ext/update")
  public DefaultCommonResult modifyDsdCodeInfoExt(
      @RequestBody @Validated DsdCodeInfoExtVO dsdCodeInfoExtVO) {
    dataStandardCodeInfoService.modifyDsdCodeInfoExt(dsdCodeInfoExtVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除码表数值信息
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/ext/delete/{id}")
  public DefaultCommonResult deleteDsdCodeInfoExt(@PathVariable("id") Integer id) {
    dataStandardCodeInfoService.deleteDsdCodeInfoExt(Long.valueOf(id).longValue());
    return DefaultCommonResult.success();
  }

  /**
   * 批量删除码表数值信息
   *
   * @param ids
   * @return DefaultCommonResult
   */
  @DeleteMapping("/ext/bulk/delete/{ids}")
  public DefaultCommonResult deleteBulkDsdCodeInfoExt(@PathVariable("ids") String ids) {
    dataStandardCodeInfoService.deleteBulkDsdCodeInfoExt(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 码表数据逆向数据库功能
   *
   * @param dsdCodeInfoReverseDBVO
   * @return DefaultCommonResult
   */
  @PostMapping("/reverse/db")
  public DefaultCommonResult dsdCodeInfoReverseDB(
      @RequestBody @Validated DsdCodeInfoReverseDBVO dsdCodeInfoReverseDBVO) {
    dataStandardCodeInfoService.dsdCodeInfoReverseDB(dsdCodeInfoReverseDBVO);
    return DefaultCommonResult.success();
  }
}
