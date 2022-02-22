package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dm.service.DataBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据库信息
 *
 * @author wjq
 * @date 20220222
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/data/base")
public class DasDataBaseController {

  private final DataBaseService dataBaseService;

  public DasDataBaseController(DataBaseService dataBaseService) {
    this.dataBaseService = dataBaseService;
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
   * 获取db库列表
   * @param connectType
   * @param dataSourceName
   * @return DefaultCommonResult<List<String>>
   */
  @GetMapping("")
  public DefaultCommonResult<List<MtdApiDb>> getAllDataBase(String connectType, String dataSourceName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllDataBase(connectType, dataSourceName));
  }

  /**
   * 获取table表列表
   * @param connectType
   * @param dataSourceName
   * @param dataBaseName
   * @return DefaultCommonResult<List<String>>
   */
  @GetMapping("/table")
  public DefaultCommonResult<List<MtdTables>> getAllTable(String connectType, String dataSourceName, String dataBaseName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllTable(connectType, dataSourceName, dataBaseName));
  }

  /**
   * 获取column字段列表
   * @param connectType
   * @param dataSourceName
   * @param dataBaseName
   * @param tableName
   * @return DefaultCommonResult<List<String>>
   */
  @GetMapping("/column")
  public DefaultCommonResult<List<String>> getAllColumn(String connectType, String dataSourceName, String dataBaseName, String tableName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dataBaseService.getAllColumn(connectType, dataSourceName, dataBaseName, tableName));
  }
}
