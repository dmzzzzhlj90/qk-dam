package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.dataquality.service.DqcRuleDataBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/29 12:07 下午
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/rule/db")
public class DqcRuleDataBaseController {

  private final DqcRuleDataBaseService dqcRuleDataBaseService;

  public DqcRuleDataBaseController(DqcRuleDataBaseService dqcRuleDataBaseService) {
    this.dqcRuleDataBaseService = dqcRuleDataBaseService;
  }

  /**
   * 数据源连接类型（引擎）
   *
   * @return DefaultCommonResult<List < String>>
   */
  @GetMapping("/connect/types")
  public DefaultCommonResult<List<String>> getAllConnType() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dqcRuleDataBaseService.getAllConnType());
  }

  /**
   * 根据数据库类型获取数据源连接信息
   *
   * @param connectType
   * @return DefaultCommonResult<List < ResultDatasourceInfo>>
   */
  @GetMapping("/connect/{connectType}")
  public DefaultCommonResult<List<ResultDatasourceInfo>> getResultDataSourceByType(
      @PathVariable("connectType") String connectType) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dqcRuleDataBaseService.getResultDataSourceByType(connectType));
  }

  /**
   * 获取db库信息下拉列表
   *
   * @param connectType
   * @return DefaultCommonResult<List < String>>
   */
  @GetMapping("")
  public DefaultCommonResult<List<String>> getAllDataBase(String connectType, String server) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dqcRuleDataBaseService.getAllDataBase(connectType, server));
  }

  /**
   * 获取table表信息下拉列表
   *
   * @param connectType,server,dataBaseName
   * @return DefaultCommonResult
   */
  @GetMapping("/table")
  public DefaultCommonResult<List<String>> getAllTable(
      String connectType, String server, String dataBaseName) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dqcRuleDataBaseService.getAllTable(connectType, server, dataBaseName));
  }

  /**
   * 获取column字段信息下拉列表
   *
   * @param connectType,server,dataBaseName,tableName
   * @return DefaultCommonResult
   */
  @GetMapping("/column")
  public DefaultCommonResult<List<String>> getAllColumn(
      String connectType, String server, String dataBaseName, String tableName) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK,
        dqcRuleDataBaseService.getAllColumn(connectType, server, dataBaseName, tableName));
  }
}
