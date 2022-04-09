package com.qk.dm.datastandards.rest;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;
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
  @PostMapping(value = "/list")
//  @Auth(bizType = BizResource.DSD_CODE_INFO, actionType = RestActionType.LIST)
  public DefaultCommonResult<PageResultVO<DsdCodeInfoVO>> searchBasicList(
      @RequestBody DsdCodeInfoParamsVO dsdCodeInfoParamsVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dataStandardCodeInfoService.getDsdCodeInfo(dsdCodeInfoParamsVO));
  }

  /**
   * 获取所有码表基础信息列表
   *
   * @return DefaultCommonResult<List < DsdCodeInfoVO>>
   */
  @PostMapping(value = "/all/list")
//  @Auth(bizType = BizResource.DSD_CODE_INFO, actionType = RestActionType.LIST)
  public DefaultCommonResult<List<DsdCodeInfoVO>> searchBasicListAll() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dataStandardCodeInfoService.getDsdCodeInfoAll());
  }

  /**
   * 新增码表基础信息
   *
   * @param dsdCodeInfoVO
   * @return DefaultCommonResult
   */
  @PostMapping("/basic")
//  @Auth(bizType = BizResource.DSD_CODE_INFO, actionType = RestActionType.CREATE)
  public DefaultCommonResult insertBasic(@RequestBody @Validated DsdCodeInfoVO dsdCodeInfoVO) {
    dataStandardCodeInfoService.addDsdCodeInfo(dsdCodeInfoVO);
    return DefaultCommonResult.success();
  }

  /**
   * 根据id获取码表信息列表详情信息
   *
   * @param id
   * @return DefaultCommonResult<DsdCodeInfoVO>
   */
  @GetMapping(value = "/basic/query/{id}")
//  @Auth(bizType = BizResource.DSD_CODE_INFO, actionType = RestActionType.DETAIL)
  public DefaultCommonResult<DsdCodeInfoVO> getBasicDsdCodeInfoById(@PathVariable("id") String id) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dataStandardCodeInfoService.getDsdCodeInfoById(Long.parseLong(id)));
  }

  /**
   * 根据tableCode(表编码)获取码表基础详情信息
   *
   * @param tableCode
   * @return DefaultCommonResult<DsdCodeInfoVO>
   */
  @GetMapping(value = "/basic/query/tableCode")
//  @Auth(bizType = BizResource.DSD_CODE_INFO, actionType = RestActionType.DETAIL)
  public DefaultCommonResult<DsdCodeInfoVO> getBasicDsdCodeInfoByTableCode(
      @RequestParam() String tableCode) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dataStandardCodeInfoService.getBasicDsdCodeInfoByTableCode(tableCode));
  }

  /**
   * 编辑码表信息
   *
   * @param dsdCodeInfoVO
   * @return DefaultCommonResult
   */
  @PutMapping("/basic")
//  @Auth(bizType = BizResource.DSD_CODE_INFO, actionType = RestActionType.UPDATE)
  public DefaultCommonResult updateBasic(@RequestBody @Validated DsdCodeInfoVO dsdCodeInfoVO) {
    dataStandardCodeInfoService.modifyDsdCodeInfo(dsdCodeInfoVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除码表信息
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/basic/{id}")
//  @Auth(bizType = BizResource.DSD_CODE_INFO, actionType = RestActionType.DELETE)
  public DefaultCommonResult deleteBasic(@PathVariable("id") Integer id) {
    dataStandardCodeInfoService.deleteDsdCodeInfo(Long.valueOf(id));
    return DefaultCommonResult.success();
  }

  /**
   * 批量删除码表信息
   *
   * @param ids
   * @return DefaultCommonResult
   */
  @DeleteMapping("/basic/bulk/{ids}")
//  @Auth(bizType = BizResource.DSD_CODE_INFO, actionType = RestActionType.DELETE)
  public DefaultCommonResult deleteBulkBasic(@PathVariable("ids") String ids) {
    dataStandardCodeInfoService.deleteBulkDsdCodeInfo(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 数据类型下拉列表
   *
   * @return0 DefaultCommonResult
   */
  @GetMapping("/data/types")
//  @Auth(bizType = BizResource.DSD_CODE_INFO, actionType = RestActionType.LIST)
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
  @PostMapping(value = "/ext/list")
//  @Auth(bizType = BizResource.DSD_CODE_INFO_EXT, actionType = RestActionType.LIST)
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
  @PostMapping("/ext")
//  @Auth(bizType = BizResource.DSD_CODE_INFO_EXT, actionType = RestActionType.CREATE)
  public DefaultCommonResult insertExt(@RequestBody @Validated DsdCodeInfoExtVO dsdCodeInfoExtVO) {
    dataStandardCodeInfoService.addDsdCodeInfoExt(dsdCodeInfoExtVO);
    return DefaultCommonResult.success();
  }

  /**
   * 根据Id获取码表数值列表详情信息
   *
   * @param id
   * @return DefaultCommonResult<DsdCodeInfoExtVO>
   */
  @GetMapping(value = "/ext/query/{id}")
//  @Auth(bizType = BizResource.DSD_CODE_INFO_EXT, actionType = RestActionType.DETAIL)
  public DefaultCommonResult<DsdCodeInfoExtVO> getBasicDsdCodeInfoExtById(
      @PathVariable("id") String id) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK,
        dataStandardCodeInfoService.getBasicDsdCodeInfoExtById(Long.parseLong(id)));
  }

  /**
   * 编辑码表数值信息
   *
   * @param dsdCodeInfoExtVO
   * @return DefaultCommonResult
   */
  @PutMapping("/ext")
//  @Auth(bizType = BizResource.DSD_CODE_INFO_EXT, actionType = RestActionType.UPDATE)
  public DefaultCommonResult updateExt(@RequestBody @Validated DsdCodeInfoExtVO dsdCodeInfoExtVO) {
    dataStandardCodeInfoService.modifyDsdCodeInfoExt(dsdCodeInfoExtVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除码表数值信息
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/ext/{id}")
//  @Auth(bizType = BizResource.DSD_CODE_INFO_EXT, actionType = RestActionType.DELETE)
  public DefaultCommonResult deleteExt(@PathVariable("id") Integer id) {
    dataStandardCodeInfoService.deleteDsdCodeInfoExt(Long.valueOf(id));
    return DefaultCommonResult.success();
  }

  /**
   * 批量删除码表数值信息
   *
   * @param ids
   * @return DefaultCommonResult
   */
  @DeleteMapping("/ext/bulk/{ids}")
//  @Auth(bizType = BizResource.DSD_CODE_INFO_EXT, actionType = RestActionType.DELETE)
  public DefaultCommonResult deleteBulkExt(@PathVariable("ids") String ids) {
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
//  @Auth(bizType = BizResource.DSD_CODE_INFO_EXT, actionType = RestActionType.CREATE)
  public DefaultCommonResult dsdCodeInfoReverseDB(
      @RequestBody @Validated DsdCodeInfoReverseDBVO dsdCodeInfoReverseDBVO) {
    dataStandardCodeInfoService.dsdCodeInfoReverseDB(dsdCodeInfoReverseDBVO);
    return DefaultCommonResult.success();
  }
}
