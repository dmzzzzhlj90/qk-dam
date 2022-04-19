package com.qk.dm.datacollect.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datacollect.service.DbDataSource;
import com.qk.dm.service.DataBaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据源连接
 * @author zys
 * @date 2022/4/18 15:00
 * @since 1.0.0
 */
@RestController
@RequestMapping("/db")
public class DtcDataBaseCollection {
  private final DataBaseService dataBaseService;
  private final DbDataSource dbDataSource;

  public DtcDataBaseCollection(DataBaseService dataBaseService,
      DbDataSource dbDataSource) {
    this.dataBaseService = dataBaseService;
    this.dbDataSource = dbDataSource;
  }

  /**
   * 获取数据源连接类型
   *
   * @return DefaultCommonResult<List < String>>
   */
  @GetMapping("/connect/types")
  public DefaultCommonResult<List<String>> getAllConnType() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllConnectType());
  }

  /**
   * 获取数据源连接
   * @param connectType
   * @return DefaultCommonResult<List<String>>
   */
  @GetMapping("/connect/{type}")
  public DefaultCommonResult<List<String>> getResultDataSourceByType(@PathVariable("type") String connectType) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllDataSource(connectType));
  }

  /**
   * 根据数据源连接名称获取数据库
   * @param dataSourceName
   * @return
   */
  @GetMapping("/connect/db/{dataSourceName}")
  public DefaultCommonResult<List<String>> getResultDb(@PathVariable("dataSourceName") String dataSourceName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dbDataSource.getResultDb(dataSourceName));
  }

}