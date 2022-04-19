package com.qk.dm.datacollect.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datacollect.service.DctDataSourceService;
import com.qk.dm.service.DataBaseService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 数据源连接
 * @author zys
 * @date 2022/4/18 15:00
 * @since 1.0.0
 */
@RestController
@RequestMapping("/db")
public class DctDataBaseCollection {
  private final DataBaseService dataBaseService;
  private final DctDataSourceService dctDataSourceService;

  public DctDataBaseCollection(DataBaseService dataBaseService,DctDataSourceService dctDataSourceService) {
    this.dataBaseService = dataBaseService;
    this.dctDataSourceService = dctDataSourceService;
  }

  /**
   * 获取数据源连接类型
   *
   * @return DefaultCommonResult<List < String>> 数据库类型
   */
  @GetMapping("/connect/types")
  public DefaultCommonResult<List<String>> getAllConnType() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllConnectType());
  }

  /**
   * 获取数据源连接
   * @param dbType 数据库类型
   * @return DefaultCommonResult<List<String>> 数据源连接名称
   */
  @GetMapping("/connect/{type}")
  public DefaultCommonResult<List<String>> getResultDataSourceByType(@PathVariable("type") String dbType) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllDataSource(dbType));
  }

  /**
   * 根据数据源连接名称获取数据库
   * @param dataSourceName 数据源连接名称
   * @return DefaultCommonResult<List<String>> 数据库信息
   */
  @GetMapping("/connect/db")
  public DefaultCommonResult<List<String>> getResultDb(@NotBlank @RequestParam String dataSourceName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dctDataSourceService.getResultDb(dataSourceName));
  }

  /**
   * 根据数据源连接、库名称获取表
   * @param dataSourceName 数据源连接名称
   * @param db 数据库名称
   * @return DefaultCommonResult<List<String>> 表信息
   */
  @GetMapping("/connect/table")
  public DefaultCommonResult<List<String>> getResultTable(@NotBlank @RequestParam String dataSourceName,@NotBlank @RequestParam String db) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dctDataSourceService.getResultTable(dataSourceName,db));
  }

}