package com.qk.dm.datasource.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datasource.entity.DsDatasource;
import com.qk.dm.datasource.service.DsDataSourceService;
import com.qk.dm.datasource.util.DsDataSouurceConnectUtil;
import com.qk.dm.datasource.vo.DsDatasourceVO;
import com.qk.dm.datasource.vo.PageResultVO;
import com.qk.dm.datasource.vo.params.DsDataSourceParamsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
   * @param dsDataSourceParamsVO
   * @return DefaultCommonResult
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
   * @param dsDatasourceVO
   * @return
   */
  @PostMapping
  public DefaultCommonResult<?> addDsDataSource(@RequestBody DsDatasourceVO dsDatasourceVO) {
    dsDataSourceService.addDsDataSource(dsDatasourceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据源连接——删除接口
   *
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  public DefaultCommonResult deleteDsDataSource(@PathVariable("id") Integer id) {
    dsDataSourceService.deleteDsDataSource(id);
    return DefaultCommonResult.success();
  }

  /**
   * 数据源连接——修改接口
   *
   * @param dsDatasourceVO
   * @return
   */
  @PutMapping
  public DefaultCommonResult updateDsDataSourece(@RequestBody DsDatasourceVO dsDatasourceVO) {
    dsDataSourceService.updateDsDataSourece(dsDatasourceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据源连接——获取数据源类型
   *
   * @return
   */
  @GetMapping("/type")
  public DefaultCommonResult<List<String>> getDataType() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsDataSourceService.getDataType());
  }

  /**
   * 数据源连接——根据数据库类型获取数据源连接信息
   *
   * @param type
   * @return DefaultCommonResult<List<DsDatasourceVO>>
   */
  @GetMapping("/database/{type}")
  public DefaultCommonResult<List<DsDatasourceVO>> getDataSourceByType(@PathVariable("type") String type) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dsDataSourceService.getDataSourceByType(type));
  }

  /**
   * 数据源连接——连接接口
   *
   * @param dsDatasourceVO
   * @return
   */
  @PostMapping("/connect")
  public DefaultCommonResult<Boolean> dsDataSoureceConnect(
      @RequestBody DsDatasourceVO dsDatasourceVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dsDataSourceService.dsDataSoureceConnect(dsDatasourceVO));
  }

  /**
   * 查询所有数据源连接类型
   *
   * @return
   */
  @GetMapping("/connect/type/all")
  public DefaultCommonResult<List<String>> getAllConnType() {
    return DefaultCommonResult.success(
            ResultCodeEnum.OK, dsDataSourceService.dsDataSourceService());
  }

  /**
   * 获取入参类型
   *
   * @param type
   * @return
   */
  @GetMapping("/param/{type}")
  public DefaultCommonResult<Object> getParamsByType(@PathVariable("type") String type) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, DsDataSouurceConnectUtil.getParamsByType(type));
  }
}
