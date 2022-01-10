package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelPhysicalDTO;
import com.qk.dm.datamodel.params.dto.ModelReverseBaseDTO;
import com.qk.dm.datamodel.params.dto.QueryModelPhysicalDTO;
import com.qk.dm.datamodel.params.vo.CensusDataVO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalTableVO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalVO;
import com.qk.dm.datamodel.service.ModelPhysicalColumnService;
import com.qk.dm.datamodel.service.ModelPhysicalTableService;
import com.qk.dm.datamodel.service.PhysicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 关系建模
 * @author zys
 * @date 2021/11/10 15:02
 * @since 1.0.0
 */
@RestController
@RequestMapping("/modelphysical")
public class ModelPhysicalController {
  @Autowired
  PhysicalService physicalService;
  @Autowired
  ModelPhysicalTableService modelPhysicalTableService;
  @Autowired
  ModelPhysicalColumnService modelPhysicalColumnService;

  public ModelPhysicalController(
      PhysicalService physicalService,
           ModelPhysicalTableService modelPhysicalTableService,
      ModelPhysicalColumnService modelPhysicalColumnService) {
    this.physicalService = physicalService;
    this.modelPhysicalTableService=modelPhysicalTableService;
    this.modelPhysicalColumnService=modelPhysicalColumnService;
  }

  /**
   * 新增——关系建模(创建结束时的保存\发布)
   *
   * @param modelPhysicalDTO
   * @return
   */
  @PostMapping
  public DefaultCommonResult add(
      @RequestBody @Validated ModelPhysicalDTO modelPhysicalDTO) {
    physicalService.insert(modelPhysicalDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除——关系建模（下线操作）
   *
   * @param ids
   * @return
   */
  @PutMapping("/delete")
  public DefaultCommonResult delete(
      @RequestBody @NotNull @Validated List<Long> ids) {
    physicalService.delete(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 修改——关系建模
   * @param modelPhysicalDTO
   * @return
   */
  @PutMapping
  public DefaultCommonResult update(
      @RequestBody @Validated ModelPhysicalDTO modelPhysicalDTO) {
    physicalService.update(modelPhysicalDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 查询——关系建模
   * @param queryModelPhysicalDTO
   * @return DefaultCommonResult<PageResultVO<ModelPhysicalVO>>
   */
  @PostMapping("/query")
  public DefaultCommonResult<PageResultVO<ModelPhysicalVO>> query(
      @RequestBody @Validated QueryModelPhysicalDTO queryModelPhysicalDTO) {
    PageResultVO<ModelPhysicalVO> pageList = physicalService.query(queryModelPhysicalDTO);
    return DefaultCommonResult.success(ResultCodeEnum.OK, pageList);
  }


  /**
   * 详情——关系建模
   * @param id
   * @return DefaultCommonResult<ModelPhysicalTableVO>
   */
  @GetMapping("/{id}")
  public DefaultCommonResult<ModelPhysicalTableVO> getModelPhysical(
      @NotNull @PathVariable("id") Long id) {
    ModelPhysicalTableVO modelPhysicalTableVO = physicalService.getModelPhysical(id);
    return DefaultCommonResult.success(ResultCodeEnum.OK, modelPhysicalTableVO);
  }

  /**
   * 统计信息——关系建模
   *
   * @param queryModelPhysicalDTO
   * @return DefaultCommonResult<CensusDataVO>
   */
  @PostMapping("/census")
  public DefaultCommonResult<CensusDataVO> getCensusData(
      @RequestBody @Validated QueryModelPhysicalDTO queryModelPhysicalDTO) {
    CensusDataVO censusDataVO = physicalService.getCensusData(queryModelPhysicalDTO);
    return DefaultCommonResult.success(ResultCodeEnum.OK, censusDataVO);
  }

  /**
   * 数据类型下拉列表——关系建模
   *
   * @return DefaultCommonResult<List<Map<String,String>>>
   */
  @GetMapping("/data/types")
  public DefaultCommonResult<List<Map<String,String>>> getDataTypes() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, physicalService.getDataTypes());
  }

  /**预览sql——关系建模
   * @param tableId
   * @return DefaultCommonResult<String>
   */
  @GetMapping("/query/sql/{tableId}")
  public DefaultCommonResult<String> getSql(@NotNull @PathVariable("tableId") Long tableId){
    return DefaultCommonResult.success(ResultCodeEnum.OK, physicalService.getSql(tableId));
  }

  /**
   * 手动同步——关系建模
   * @param physicalIds
   * @return
   */
  @PutMapping("/synchronization")
  public DefaultCommonResult synchronization(@RequestBody @NotNull @Validated List<Long> physicalIds){
    physicalService.synchronization(physicalIds);
    return DefaultCommonResult.success();
  }

  /**
   *批量发布——关系建模
   * @param idList
   * @return
   */
  @PutMapping("/push")
  public DefaultCommonResult push(@RequestBody @NotNull @Validated List<Long> idList){
    physicalService.push(idList);
    return DefaultCommonResult.success();
  }

  /**
   * 逆向数据库
   * @param modelReverseBaseDTO
   * @return
   */
  @PostMapping("/reverse/base")
  public DefaultCommonResult reverseBase(@RequestBody @Validated ModelReverseBaseDTO modelReverseBaseDTO){
    physicalService.reverseBase(modelReverseBaseDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 新建关系获取表名称（上线表的表名称）
   * @return DefaultCommonResult<List<String>>
   */
  @GetMapping("/tables")
  public DefaultCommonResult<List<String>> queryTables(){
    return  DefaultCommonResult.success(ResultCodeEnum.OK,modelPhysicalTableService.queryTables());
  }

  /**
   * 根据表名称查询表字段名称
   * @param tableName
   * @return DefaultCommonResult<List<String>>
   */
  @GetMapping("/column")
  public DefaultCommonResult<List<String>> queryColumn(@NotNull @RequestParam("tableName") String tableName){
    return DefaultCommonResult.success(ResultCodeEnum.OK,modelPhysicalColumnService.queryColumn(tableName));
  }

  /**
   * 基础信息—查询数据格式
   * @return DefaultCommonResult<List<String>>
   */
  @GetMapping("/data/fromat")
  public DefaultCommonResult<List<String>> queryDataType(){
    return DefaultCommonResult.success(ResultCodeEnum.OK, physicalService.queryDataType());
  }
  //============================数据连接调用=========================================>

  /**
   * 查询所有数据源连接类型
   *
   * @return DefaultCommonResult
   */
 /* @GetMapping("/datasource/api/type/all")
  public DefaultCommonResult<List<String>> getAllConnType() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, datasourceService.getAllConnType());
  }*/

  /**
   * 根据数据库类型获取数据源连接信息
   *
   * @return DefaultCommonResult
   */
  /*@GetMapping("/datasource/api/database/{type}")
  public DefaultCommonResult<List<ResultDatasourceInfo>> getResultDataSourceByType(
      @PathVariable("type") String type) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, datasourceService.getResultDataSourceByType(type));
  }*/

  /**
   * 根据数据源名称获取数据源连接信息
   *
   * @return DefaultCommonResult
   */
  /*@GetMapping("/datasource/api/name/{connectName}")
  public DefaultCommonResult<ResultDatasourceInfo> getResultDataSourceByConnectName(
      @PathVariable("connectName") String connectName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, datasourceService.getResultDataSourceByConnectName(connectName));
  }*/
  //=======================================元数据==================================================>

  /**
   * 获取元数据表信息
   * @param mtdTableApiParams
   * @return
   */
  /*@PostMapping("/meta/api/tables")
  public DefaultCommonResult<List<MtdTables>> getTables(@RequestBody MtdTableApiParams mtdTableApiParams) {
    return DefaultCommonResult.success(ResultCodeEnum.OK,metaDataService.getTables(mtdTableApiParams));
  }*/

  /**
   * 获取元数据表字段信息
   * @param guid
   * @return
   */
  /*@GetMapping("/columns/{guid}")
  public DefaultCommonResult<List<Map<String, Object>>> getColumns(@PathVariable("guid") String guid) {
    return DefaultCommonResult.success(ResultCodeEnum.OK,metaDataService.getColumns(guid));
  }*/
}