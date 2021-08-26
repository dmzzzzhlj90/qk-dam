package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metedata.entity.MtdApi;
import com.qk.dam.metedata.entity.MtdApiParams;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dm.dataservice.service.DasApiDataSourceConfigService;
import com.qk.dm.dataservice.vo.DasApiDataSourceConfigVO;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据服务_新建API
 *
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/create")
public class DasApiDataSourceConfigController {
  private final DasApiDataSourceConfigService dasApiDataSourceConfigService;

  @Autowired
  public DasApiDataSourceConfigController(
      DasApiDataSourceConfigService dasApiDataSourceConfigService) {
    this.dasApiDataSourceConfigService = dasApiDataSourceConfigService;
  }

  /**
   * 新增API_详情展示
   *
   * @param apiId
   * @return DefaultCommonResult<PageResultVO < DasDataSourceConfigVO>>
   */
  @GetMapping(value = "/query/{apiId}")
  public DefaultCommonResult<DasApiDataSourceConfigVO> getDasDataSourceConfigInfoByApiId(
      @PathVariable("apiId") String apiId) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiDataSourceConfigService.getDasDataSourceConfigInfoByApiId(apiId));
  }

  /**
   * 新增API基础信息
   *
   * @param dasDataSourceConfigVO
   * @return DefaultCommonResult
   */
  @PostMapping("/add")
  public DefaultCommonResult addDasDataSourceConfig(
      @RequestBody @Validated DasApiDataSourceConfigVO dasDataSourceConfigVO) {
    dasApiDataSourceConfigService.addDasDataSourceConfig(dasDataSourceConfigVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑API基础信息
   *
   * @param dasDataSourceConfigVO
   * @return DefaultCommonResult
   */
  @PutMapping("/update")
  public DefaultCommonResult updateDasDataSourceConfig(
      @RequestBody @Validated DasApiDataSourceConfigVO dasDataSourceConfigVO) {
    dasApiDataSourceConfigService.updateDasDataSourceConfig(dasDataSourceConfigVO);
    return DefaultCommonResult.success();
  }

  /**
   * 新建API__请求参数表头
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/request/paras/header/infos")
  public DefaultCommonResult<Map<String, String>> getDSConfigRequestParaHeaderInfo() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiDataSourceConfigService.getDSConfigRequestParaHeaderInfo());
  }

  /**
   * 新建API__返回参数表头
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/response/paras/header/infos")
  public DefaultCommonResult<Map<String, String>> getDSConfigResponseParaHeaderInfo() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiDataSourceConfigService.getDSConfigResponseParaHeaderInfo());
  }

  /**
   * 新建API__排序参数表头
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/query/order/paras/header/infos")
  public DefaultCommonResult<Map<String, String>> getDSConfigOrderParaHeaderInfo() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiDataSourceConfigService.getDSConfigOrderParaHeaderInfo());
  }

  /**
   * 新建API__参数操作比较符号
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/paras/compare/symbol")
  public DefaultCommonResult<List<String>> getDSConfigParasCompareSymbol() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiDataSourceConfigService.getDSConfigParasCompareSymbol());
  }

  /**
   * 新建API__排序方式
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/paras/sort/style")
  public DefaultCommonResult<Map<String, String>> getDSConfigParasSortStyle() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiDataSourceConfigService.getDSConfigParasSortStyle());
  }

  // ========================数据源服务API调用=====================================

  /**
   * 查询所有数据源连接类型
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/datasource/api/type/all")
  public DefaultCommonResult<List<String>> getAllConnType() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiDataSourceConfigService.getAllConnType());
  }

  /**
   * 根据数据库类型获取数据源连接信息
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/datasource/api/database/{type}")
  DefaultCommonResult<List<ResultDatasourceInfo>> getResultDataSourceByType(
      @PathVariable("type") String type) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiDataSourceConfigService.getResultDataSourceByType(type));
  }

  /**
   * 根据数据源名称获取数据源连接信息
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/datasource/api/name/{connectName}")
  public DefaultCommonResult<ResultDatasourceInfo> getResultDataSourceByConnectName(
      @PathVariable("connectName") String connectName) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK,
        dasApiDataSourceConfigService.getResultDataSourceByConnectName(connectName));
  }

  // ========================数据源服务API调用=====================================

  /**
   * 获取所有的元数据类型
   *
   * @return DefaultCommonResult<List < MtdAtlasEntityType>>
   */
  @GetMapping("/mtd/api/all/entity/type")
  public DefaultCommonResult<List<MtdAtlasEntityType>> getAllEntityType() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiDataSourceConfigService.getAllEntityType());
  }

  /**
   * 获取元数据详情信息
   *
   * @param mtdApiParams
   * @return DefaultCommonResult<MtdApi>
   */
  @PostMapping("/mtd/detail")
  DefaultCommonResult<MtdApi> mtdDetail(@RequestBody @Validated MtdApiParams mtdApiParams) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiDataSourceConfigService.mtdDetail(mtdApiParams));
  }
}
