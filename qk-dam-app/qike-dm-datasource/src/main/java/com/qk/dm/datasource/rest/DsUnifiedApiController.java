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
public class DsUnifiedApiController {
  private final DsUnifiedApiService dsUnifiedApiService;

  public DsUnifiedApiController(
      DsUnifiedApiService dsUnifiedApiService) {
    this.dsUnifiedApiService = dsUnifiedApiService;
  }
  /**----------------------------------------------------获取数据类型、根据数据源连接---------------------------------------------------*/
  /**
   * 获取所有数据源连接类型
   *
   * @return DefaultCommonResult<List < String>>
   */
  @GetMapping("/dbtype/all")
  public DefaultCommonResult<List<String>> getAllConnType() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getAllConnType());
  }

  /**
   * 获取数据源连接（根据数据库类型）
   * @param type 数据库类型
   * @return DefaultCommonResult<Map<String,String>> 数据源连接名称
   */
  @GetMapping("/connect/info/{type}")
  public DefaultCommonResult<Map<String,String>> getResultDataSourceByType(@NotBlank @PathVariable("type") String type) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getAllDataSourcesByType(type));
  }
  /**----------------------------------------------------获取atals库、表信息-------------------------------------------------------------*/

  /**
   * 获取mtd(元数据)中db库信息
   * @param type 数据库类型
   * @param dataSourceCode 数据源connId
   * @return DefaultCommonResult<List<MtdApiDb>> 库信息
   */
  @GetMapping("/mtd/db")
  public DefaultCommonResult<List<MtdApiDb>> getAllDataBase(@NotBlank @RequestParam("type") String type,@NotBlank @RequestParam("dataSourceCode") String dataSourceCode) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getAllDataBase(type, dataSourceCode));
  }

  /**
   * 获取mtd(元数据)中表信息
   * @param type 数据库类型
   * @param dataSourceCode 数据源标识code编码
   * @param dbName 数据库名称
   * @return DefaultCommonResult<List<MtdTables>> 表信息
   */
  @GetMapping("/mtd/table")
  public DefaultCommonResult<List<MtdTables>> getAllTable(@RequestParam ("type") String type, @RequestParam ("dataSourceCode") String dataSourceCode, @RequestParam("dbName") String dbName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getAllTable(type, dataSourceCode, dbName));
  }

  /**
   * 获取元数据(mtd)中字段信息
   * @param type 数据库类型
   * @param dataSourceCode 数据源标识code编码
   * @param dbName 数据库名称
   * @param tableName 表名称
   * @return List<MtdAttributes> 字段信息
   */
  @GetMapping("/mtd/column")
  public DefaultCommonResult<List<MtdAttributes>> getAllColumn(@RequestParam ("type") String type, @RequestParam ("dataSourceCode") String dataSourceCode, @RequestParam("dbName") String dbName, @RequestParam ("tableName") String tableName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getAllColumn(type, dataSourceCode, dbName, tableName));
  }
  /**----------------------------------------------------获取information_schema（直连direct）库、表信息-------------------------------------------------*/

  /**
   * 获取直连(native)中db库信息
   * @param dataSourceCode 数据源标识code编码
   * @return DefaultCommonResult<List<String>> 数据库信息
   */
  @GetMapping("/native/db")
  public DefaultCommonResult<List<String>> getDctResultDb(@NotBlank @RequestParam ("dataSourceCode") String dataSourceCode) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getDctResultDb(dataSourceCode));
  }

  /**
   * 获取直连(native)中表信息
   * @param dataSourceCode 数据源标识code编码
   * @param dbName 数据库名称
   * @return DefaultCommonResult<List<String>> 表信息
   */
  @GetMapping("/native/table")
  public DefaultCommonResult<List<String>> getDctResultTable(@NotBlank @RequestParam ("dataSourceCode") String dataSourceCode,@NotBlank @RequestParam ("dbName") String dbName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsUnifiedApiService.getDctResultTable(dataSourceCode,dbName));
  }


}