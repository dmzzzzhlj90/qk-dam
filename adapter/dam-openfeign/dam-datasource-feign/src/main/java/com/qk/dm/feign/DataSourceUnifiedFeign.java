package com.qk.dm.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdAttributes;
import com.qk.dam.metedata.entity.MtdTables;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@FeignClient(value = "dm-datasource-${spring.profiles.active}", path = "/ds")
@Component
public interface DataSourceUnifiedFeign {
  /**----------------------------------------------------获取数据类型、根据数据源连接---------------------------------------------------*/
  /**
   * 获取所有数据源连接类型
   *
   * @return DefaultCommonResult<List < String>>
   */
  @GetMapping("/dbtype/all")
  DefaultCommonResult<List<String>> getAllConnType();

  /**
   * 获取数据源连接（根据数据库类型）
   * @param type 数据库类型
   * @return DefaultCommonResult<Map<String,String>> 数据源连接名称
   */
  @GetMapping("/connect/info/{type}")
  DefaultCommonResult<Map<String,String>> getResultDataSourceByType(@PathVariable("type") String type);

  /**----------------------------------------------------获取atals库、表信息-------------------------------------------------------------*/

  /**
   * 获取mtd(元数据)中db库信息
   * @param type 数据库类型
   * @param dataSourceCode 数据源connId
   * @return DefaultCommonResult<List<MtdApiDb>> 库信息
   */
  @GetMapping("/mtd/db")
  DefaultCommonResult<List<MtdApiDb>> getAllDataBase(@NotBlank @RequestParam("type") String type,@NotBlank @RequestParam("dataSourceCode") String dataSourceCode);

  /**
   * 获取mtd(元数据)中表信息
   * @param type 数据库类型
   * @param dataSourceCode 数据源标识code编码
   * @param dbName 数据库名称
   * @return DefaultCommonResult<List<MtdTables>> 表信息
   */
  @GetMapping("/mtd/table")
  DefaultCommonResult<List<MtdTables>> getAllTable(@RequestParam ("type") String type, @RequestParam ("dataSourceCode") String dataSourceCode, @RequestParam("dbName") String dbName);

  /**
   * 获取元数据(mtd)中字段信息
   * @param type 数据库类型
   * @param dataSourceCode 数据源标识code编码
   * @param dbName 数据库名称
   * @param tableName 表名称
   * @return List<MtdAttributes> 字段信息
   */
  @GetMapping("/mtd/column")
  DefaultCommonResult<List<MtdAttributes>>getAllColumn(@RequestParam ("type") String type, @RequestParam ("dataSourceCode") String dataSourceCode, @RequestParam("dbName") String dbName, @RequestParam ("tableName") String tableName);
  /**----------------------------------------------------获取information_schema（直连direct）库、表信息-------------------------------------------------*/
  /**
   * 获取直连(native)中db库信息
   * @param dataSourceCode 数据源标识code编码
   * @return DefaultCommonResult<List<String>> 数据库信息
   */
  @GetMapping("/native/db")
  DefaultCommonResult<List<String>> getDctResultDb(@NotBlank @RequestParam ("dataSourceCode") String dataSourceCode);

  /**
   * 获取直连(native)中表信息
   * @param dataSourceCode 数据源标识code编码
   * @param dbName 数据库名称
   * @return DefaultCommonResult<List<String>> 表信息
   */
  @GetMapping("/native/table")
  DefaultCommonResult<List<String>> getDctResultTable(@NotBlank @RequestParam ("dataSourceCode") String dataSourceCode,@NotBlank @RequestParam ("dbName") String dbName);
}
