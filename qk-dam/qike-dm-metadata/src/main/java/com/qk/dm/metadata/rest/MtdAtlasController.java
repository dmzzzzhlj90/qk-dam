package com.qk.dm.metadata.rest;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dm.metadata.service.AtlasMetaDataService;
import com.qk.dm.metadata.vo.*;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 元数据查询相关接口（元数据列表查询、元数据详情、元数据类型等接口）
 *
 * @author wangzp
 * @date 2021/8/06 15:05
 * @since 1.0.0
 */
@RestController
public class MtdAtlasController {

  private final AtlasMetaDataService atlasMetaDataService;

  public MtdAtlasController(AtlasMetaDataService atlasMetaDataService) {
    this.atlasMetaDataService = atlasMetaDataService;
  }

  /**
   * 获取所有的基础类型
   *
   * @return DefaultCommonResult<Map<String, List<MtdAtlasEntityTypeVO>>>
   */
  @GetMapping("/basic/types")
//  @Auth(bizType = BizResource.MTD_BASIC_TYPE, actionType = RestActionType.LIST)
  public DefaultCommonResult<Map<String, List<MtdAtlasEntityType>>> getAllEntityType() {
    Map<String, List<MtdAtlasEntityType>> mtdAtlasEntityTypeVOList =
        atlasMetaDataService.getAllEntityType();
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdAtlasEntityTypeVOList);
  }

  /**
   * 查询元数据列表
   *
   * @param mtdAtlasParams 查询参数
   * @return DefaultCommonResult<List<MtdAtlasBaseVO>>
   */
  @PostMapping("/list")
//  @Auth(bizType = BizResource.MTD_ENTITY, actionType = RestActionType.LIST)
  public DefaultCommonResult<List<MtdAtlasBaseVO>> searchList(
      @RequestBody @Validated MtdAtlasParamsVO mtdAtlasParams) {
    List<MtdAtlasBaseVO> atlasBaseMainDataVOList = atlasMetaDataService.searchList(mtdAtlasParams);
    return DefaultCommonResult.success(ResultCodeEnum.OK, atlasBaseMainDataVOList);
  }

  /**
   * 多条件查询元数据列表
   *
   * @param mtdAtlasBaseSearch 条件参数
   * @return DefaultCommonResult<List<MtdAtlasBaseVO>>
   */
  @PostMapping("/criteria/list")
//  @Auth(bizType = BizResource.MTD_CRITERIA_ENTITY, actionType = RestActionType.LIST)
  public DefaultCommonResult<List<MtdAtlasBaseVO>> searchListByCriteria(
      @RequestBody @Validated MtdAtlasBaseSearchVO mtdAtlasBaseSearch) {
    List<MtdAtlasBaseVO> atlasBaseMainDataVOList =
        atlasMetaDataService.searchList(mtdAtlasBaseSearch, true);
    return DefaultCommonResult.success(ResultCodeEnum.OK, atlasBaseMainDataVOList);
  }


  /**
   * 根据guid获取数据库的元数据信息
   *
   * @param guid 元数据唯一id
   * @return DefaultCommonResult<MtdAtlasDbDetailVO>
   */
  @GetMapping("/db/{guid}")
//  @Auth(bizType = BizResource.MTD_ENTITY_DB, actionType = RestActionType.DETAIL)
  public DefaultCommonResult<MtdDbDetailVO> getDbDetailByGuid(@PathVariable("guid") String guid) {
    MtdDbDetailVO mtdAtlasDbDetailVO = atlasMetaDataService.getDbDetailByGuid(guid);
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdAtlasDbDetailVO);
  }
  /**
   * 根据guid获取表的元数据详情
   *
   * @param guid 元数据唯一id
   * @return DefaultCommonResult<MtdAtlasTableDetailVO>
   */
  @GetMapping("/table/{guid}")
//  @Auth(bizType = BizResource.MTD_ENTITY, actionType = RestActionType.DETAIL)
  public DefaultCommonResult<MtdTableDetailVO> getTableDetailByGuid(
      @PathVariable("guid") String guid) {
    MtdTableDetailVO mtdTableDetailVO = atlasMetaDataService.getTableDetailByGuid(guid);
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdTableDetailVO);
  }

  /**
   * 根据guid获取列元数据详情
   *
   * @param guid 元数据唯一id
   * @return DefaultCommonResult<MtdColumnVO>
   */
  @GetMapping("/column/{guid}")
//  @Auth(bizType = BizResource.MTD_ENTITY, actionType = RestActionType.DETAIL)
  public DefaultCommonResult<MtdColumnVO> getColumnDetailByGuid(@PathVariable("guid") String guid) {
    MtdColumnVO mtdColumnVO = atlasMetaDataService.getColumnDetailByGuid(guid);
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdColumnVO);
  }

  /**
   * 获取数据库下的表
   * @param mtdTableParaVO
   * @return List<MtdTableVO>
   */
  @PostMapping("/table/info/list")
  public DefaultCommonResult<List<MtdTableVO>> getTableList(@RequestBody @Validated MtdTableParaVO mtdTableParaVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,atlasMetaDataService.getTableList(mtdTableParaVO));
  }

  /**
   * 根据guid删除元数据,多个guid 使用英文逗号分割
   *
   * @param guids
   * @return DefaultCommonResult
   */
  @DeleteMapping("/{guids}")
//  @Auth(bizType = BizResource.MTD_ENTITY, actionType = RestActionType.DELETE)
  public DefaultCommonResult deleteEntitiesByGuids(@PathVariable("guids") String guids) {
    atlasMetaDataService.deleteEntitiesByGuids(guids);
    return DefaultCommonResult.success(ResultCodeEnum.OK, null);
  }
}
