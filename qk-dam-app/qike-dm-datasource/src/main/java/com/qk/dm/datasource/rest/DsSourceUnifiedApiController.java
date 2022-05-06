package com.qk.dm.datasource.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdAttributes;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dm.datasource.service.DsUnifiedApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 数据源连接统一api
 * @author zys
 * @date 2022/4/29 15:05
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/unified/api")
public class DsSourceUnifiedApiController {
  private final DsUnifiedApiService dsUnifiedApiService;

  public DsSourceUnifiedApiController(
      DsUnifiedApiService dsUnifiedApiService) {
    this.dsUnifiedApiService = dsUnifiedApiService;
  }
  /**----------------------------------------------------获取数据类型、根据数据源连接---------------------------------------------------*/
  /**
   * 获取所有数据源连接类型
   *
   * @return DefaultCommonResult<List < String>>
   */
  @GetMapping("/dbtype")
  public DefaultCommonResult<List<String>> getAllConnType() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getAllConnType());
  }

  /**
   * 获取数据源连接（根据数据库类型）
   * @param engineType 适用引擎
   * @return DefaultCommonResult<Map<String,String>> 数据源连接名称
   */
  @GetMapping("/connect/{engineType}")
  public DefaultCommonResult<Map<String,String>> getResultDataSourceByType(@NotBlank @PathVariable("engineType") String engineType) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getAllDataSourcesByType(engineType));
  }
  /**----------------------------------------------------获取atals库、表信息-------------------------------------------------------------*/

  /**
   * 获取mtd(元数据)中db库信息
   * @param engineType 数据库类型
   * @param dataSourceConnId 数据源connId
   * @return DefaultCommonResult<List<MtdApiDb>> 库信息
   */
  @GetMapping("/mtd/db")
  public DefaultCommonResult<List<MtdApiDb>> getAllDataBase(@NotBlank @RequestParam String engineType,@NotBlank @RequestParam String dataSourceConnId) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getAllDataBase(engineType, dataSourceConnId));
  }

  /**
   * 获取mtd(元数据)中表信息
   * @param engineType 数据库类型
   * @param dataSourceConnId 数据源connId
   * @param dataBaseName 数据库名称
   * @return DefaultCommonResult<List<MtdTables>> 表信息
   */
  @GetMapping("/mtd/table")
  public DefaultCommonResult<List<MtdTables>> getAllTable(String engineType, String dataSourceConnId, String dataBaseName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getAllTable(engineType, dataSourceConnId, dataBaseName));
  }

  /**
   * 获取mtd(元数据)中字段信息
   * @param engineType 数据库类型
   * @param dataSourceConnId 数据源connId
   * @param dataBaseName 数据库名称
   * @param tableName 表名称
   * @return List<MtdAttributes> 字段信息
   */
  @GetMapping("/mtd/column")
  public DefaultCommonResult<List<MtdAttributes>> getAllColumn(String engineType, String dataSourceConnId, String dataBaseName, String tableName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getAllColumn(engineType, dataSourceConnId, dataBaseName, tableName));
  }
  /**----------------------------------------------------获取information_schema（直连direct）库、表信息-------------------------------------------------*/

  /**
   * 获取直连(dct)中db库信息
   * @param dataSourceConnId 数据源连接id
   * @return DefaultCommonResult<List<String>> 数据库信息
   */
  @GetMapping("/dct/db")
  public DefaultCommonResult<List<String>> getDctResultDb(@NotBlank @RequestParam String dataSourceConnId) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getDctResultDb(dataSourceConnId));
  }

  /**
   * 获取直连(dct)中表信息
   * @param dataSourceConnId 数据源连接id
   * @param databaseName 数据库名称
   * @return DefaultCommonResult<List<String>> 表信息
   */
  @GetMapping("/dct/table")
  public DefaultCommonResult<List<String>> getDctResultTable(@NotBlank @RequestParam String dataSourceConnId,@NotBlank @RequestParam String databaseName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getDctResultTable(dataSourceConnId,databaseName));
  }


}