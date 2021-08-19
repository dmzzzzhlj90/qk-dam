package com.qk.dm.datasource.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datasource.service.DsDataSourceService;
import com.qk.dm.datasource.vo.DsDatasourceVO;
import com.qk.dm.datasource.vo.PageResultVO;
import com.qk.dm.datasource.vo.params.DsDataSourceParamsVO;
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
  @PostMapping("/add")
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
  @DeleteMapping("/delete/{id}")
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
  @PostMapping("/update")
  public DefaultCommonResult updateDsDataSourece(@RequestBody DsDatasourceVO dsDatasourceVO) {
    dsDataSourceService.updateDsDataSourece(dsDatasourceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据源连接——获取连接类型接口
   *
   * @return
   */
  @PostMapping("atlas/getdatatype")
  public DefaultCommonResult<List<String>> getDataType() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsDataSourceService.getDataType());
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
}
