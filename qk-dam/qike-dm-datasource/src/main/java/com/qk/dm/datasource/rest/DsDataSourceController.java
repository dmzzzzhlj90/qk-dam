package com.qk.dm.datasource.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datasource.service.DsDataSourceService;
import com.qk.dm.datasource.util.DsDataSouurceConnectUtil;
import com.qk.dm.datasource.vo.DsDataSourceParamsVO;
import com.qk.dm.datasource.vo.DsDatasourceVO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据管理—数据源连接
 *
 * @author zys
 * @date 2021/8/2 10:57
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/datasource")
public class DsDataSourceController {
  private final DsDataSourceService dsDataSourceService;

  @Autowired
  public DsDataSourceController(DsDataSourceService dsDataSourceService) {
    this.dsDataSourceService = dsDataSourceService;
  }
  /**
   * 数据源连接——统一查询接口
   *
   * @param dsDataSourceParamsVO 数据源参数
   * @return DefaultCommonResult<PageResultVO<DsDatasourceVO>> 分页查询数据源信息
   */
  @PostMapping("/query")
  public DefaultCommonResult<PageResultVO<DsDatasourceVO>> getDsDataSource(
      @RequestBody DsDataSourceParamsVO dsDataSourceParamsVO) {
    PageResultVO<DsDatasourceVO> dsDataSource =
        dsDataSourceService.getDsDataSource(dsDataSourceParamsVO);
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsDataSource);
  }

  /**
   * 数据源连接——新增接口
   *
   * @param dsDatasourceVO 数据源信息
   * @return DefaultCommonResult
   */
  @PostMapping
  public DefaultCommonResult addDsDataSource(@RequestBody DsDatasourceVO dsDatasourceVO) {
    dsDataSourceService.addDsDataSource(dsDatasourceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据源连接——删除接口
   *
   * @param id 数据源id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/{id}")
  public DefaultCommonResult deleteDsDataSource(@PathVariable("id") Integer id) {
    dsDataSourceService.deleteDsDataSource(id);
    return DefaultCommonResult.success();
  }

  /**
   * 数据源连接——修改接口
   *
   * @param dsDatasourceVO 查询条件
   * @return DefaultCommonResult 成功
   */
  @PutMapping
  public DefaultCommonResult updateDsDataSource(@RequestBody DsDatasourceVO dsDatasourceVO) {
    dsDataSourceService.updateDsDataSource(dsDatasourceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据源连接——获取数据源类型
   *
   * @return DefaultCommonResult<List<String>> 数据源类型
   */
  @GetMapping("/type")
  public DefaultCommonResult<List<String>> getDataType() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsDataSourceService.getDataType());
  }

  /**
   * 数据源连接——根据数据库类型获取数据源连接信息
   *
   * @param type 数据连接类型
   * @return DefaultCommonResult<List<DsDatasourceVO>> 数据源信息
   */
  @GetMapping("/database/{type}")
  public DefaultCommonResult<List<DsDatasourceVO>> getDataSourceByType(
      @PathVariable("type") String type) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dsDataSourceService.getDataSourceByType(type));
  }

  /**
   * 数据源连接——根据数据源名称获取数据源连接信息
   *
   * @param dataSourceName 数据源名称
   * @return DefaultCommonResult<List<DsDatasource>> 数据源信息
   */
  @GetMapping("/name/{dataSourceName}")
  public DefaultCommonResult<List<DsDatasourceVO>> getDataSourceByDsname(
      @PathVariable("dataSourceName") String dataSourceName) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dsDataSourceService.getDataSourceByDsname(dataSourceName));
  }

  /**
   * 数据源连接——测试连接
   *
   * @param dsDatasourceVO 数据信息
   * @return DefaultCommonResult<Boolean> true:连接成功，false:连接失败
   */
  @PostMapping("/connecting")
  public DefaultCommonResult<Boolean> dataSourceConnect(
      @RequestBody DsDatasourceVO dsDatasourceVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dsDataSourceService.dataSourceConnect(dsDatasourceVO));
  }

  /**
   * 数据源连接——获取入参类型
   *
   * @param type 数据库类型
   * @return DefaultCommonResult<Object> 返回入参信息
   */
  @GetMapping("/in/params/{type}")
  public DefaultCommonResult<Object> getParamsByType(@PathVariable("type") String type) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, DsDataSouurceConnectUtil.getParamsByType(type));
  }
}
