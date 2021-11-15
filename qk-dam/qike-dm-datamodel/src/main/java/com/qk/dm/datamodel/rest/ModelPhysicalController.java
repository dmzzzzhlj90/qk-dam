package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelPhysicalDTO;
import com.qk.dm.datamodel.params.dto.QueryModelPhysicalDTO;
import com.qk.dm.datamodel.params.vo.CensusDataVO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalTableVO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalVO;
import com.qk.dm.datamodel.service.DatasourceService;
import com.qk.dm.datamodel.service.PhysicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 关系建模
 * @author zys
 * @date 2021/11/10 15:02
 * @since 1.0.0
 */
@RestController
@RequestMapping("/modelphysical")
public class ModelPhysicalController {
  @Autowired PhysicalService physicalService;
  @Autowired DatasourceService datasourceService;
  public ModelPhysicalController(PhysicalService physicalService,DatasourceService datasourceService){
    this.physicalService = physicalService;
    this.datasourceService=datasourceService;
  }

  /**
   * 新增——关系建模
   * @param modelPhysicalDTO
   * @return
   */
  @PostMapping
  public DefaultCommonResult add(@RequestBody @Validated ModelPhysicalDTO modelPhysicalDTO){
    physicalService.insert(modelPhysicalDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除——关系建模（下线操作）
   * @param ids
   * @return
   */
  @PutMapping("/delete")
  public DefaultCommonResult delete(@RequestBody @NotNull @Validated List<Long> ids){
    physicalService.delete(ids);
    return  DefaultCommonResult.success();
  }

  /**
   * 修改——关系建模
   */

  @PutMapping
  public DefaultCommonResult update(@RequestBody @Validated ModelPhysicalDTO modelPhysicalDTO){
    physicalService.update(modelPhysicalDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 查询——关系建模
   * @return
   */
  @PostMapping("/query")
  public DefaultCommonResult<PageResultVO<ModelPhysicalVO>> query(@RequestBody @Validated QueryModelPhysicalDTO queryModelPhysicalDTO){
    PageResultVO<ModelPhysicalVO> pageList= physicalService.query(queryModelPhysicalDTO);
    return DefaultCommonResult.success(ResultCodeEnum.OK,pageList);
  }
  /**
   * 根据id查询基本信息
   */
  @GetMapping("/{id}")
  public DefaultCommonResult<ModelPhysicalTableVO> getModelPhysical(@NotNull @PathVariable("id") Long id){
    ModelPhysicalTableVO modelPhysicalTableVO = physicalService.getModelPhysical(id);
    return DefaultCommonResult.success(ResultCodeEnum.OK,modelPhysicalTableVO);
  }

  /**
   * 根据层级和主题查询相关数据统计信息
   * @param queryModelPhysicalDTO
   * @return
   */
  @PostMapping("/census")
  public DefaultCommonResult<CensusDataVO> getCensusData(@RequestBody @Validated QueryModelPhysicalDTO queryModelPhysicalDTO){
    CensusDataVO censusDataVO = physicalService.getCensusData(queryModelPhysicalDTO);
    return DefaultCommonResult.success(ResultCodeEnum.OK,censusDataVO);
  }
  //============================数据连接调用=========================================>

  /**
   * 查询所有数据源连接类型
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/datasource/api/type/all")
  public DefaultCommonResult<List<String>> getAllConnType() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, datasourceService.getAllConnType());
  }

  /**
   * 根据数据库类型获取数据源连接信息
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/datasource/api/database/{type}")
  DefaultCommonResult<List<ResultDatasourceInfo>> getResultDataSourceByType(
      @PathVariable("type") String type) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, datasourceService.getResultDataSourceByType(type));
  }

  /**
   * 根据数据源名称获取数据源连接信息
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/datasource/api/name/{connectName}")
  public DefaultCommonResult<ResultDatasourceInfo> getResultDataSourceByConnectName(
      @PathVariable("connectName") String connectName) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, datasourceService.getResultDataSourceByConnectName(connectName));
  }
}