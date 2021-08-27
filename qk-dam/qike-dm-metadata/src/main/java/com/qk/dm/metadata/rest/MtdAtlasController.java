package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dm.metadata.service.AtlasMetaDataService;
import com.qk.dm.metadata.vo.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
  @GetMapping("/allEntityType")
  public DefaultCommonResult<Map<String, List<MtdAtlasEntityType>>> getAllEntityType() {
    Map<String, List<MtdAtlasEntityType>> mtdAtlasEntityTypeVOList =
        atlasMetaDataService.getAllEntityType();
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdAtlasEntityTypeVOList);
  }

  /**
   * 查询元数据列表
   *
   * @param mtdAtlasParamsVO
   * @return DefaultCommonResult<List<MtdAtlasBaseVO>>
   */
  @PostMapping("/list")
  public DefaultCommonResult<List<MtdAtlasBaseVO>> searchList(
      @RequestBody @Validated MtdAtlasParamsVO mtdAtlasParamsVO) {
    List<MtdAtlasBaseVO> atlasBaseMainDataVOList =
        atlasMetaDataService.searchList(mtdAtlasParamsVO);
    return DefaultCommonResult.success(ResultCodeEnum.OK, atlasBaseMainDataVOList);
  }

  /**
   * 多条件查询元数据列表
   * @param mtdAtlasBaseSearchVO
   * @return DefaultCommonResult<List<MtdAtlasBaseVO>>
   */
  @PostMapping("/list/criteria")
  public DefaultCommonResult<List<MtdAtlasBaseVO>> searchListByCriteria(
      @RequestBody @Validated MtdAtlasBaseSearchVO mtdAtlasBaseSearchVO) {
    List<MtdAtlasBaseVO> atlasBaseMainDataVOList =
        atlasMetaDataService.searchList(mtdAtlasBaseSearchVO, true);
    return DefaultCommonResult.success(ResultCodeEnum.OK, atlasBaseMainDataVOList);
  }



  /**
   * 根据guid获取数据库的元数据信息
   *
   * @param guid 元数据唯一id
   * @return DefaultCommonResult<MtdAtlasDbDetailVO>
   */
  @GetMapping("/techno/db/detail/{guid}")
  public DefaultCommonResult<MtdDbDetailVO> getDbDetailByGuid(
          @PathVariable("guid") String guid) {
    MtdDbDetailVO mtdAtlasDbDetailVO = atlasMetaDataService.getDbDetailByGuid(guid);
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdAtlasDbDetailVO);
  }
  /**
   * 根据guid获取表的元数据详情
   *
   * @param guid 元数据唯一id
   * @return DefaultCommonResult<MtdAtlasTableDetailVO>
   */
  @GetMapping("/techno/table/detail/{guid}")
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
  @GetMapping("/techno/column/detail/{guid}")
  public DefaultCommonResult<MtdColumnVO> getColumnDetailByGuid(
          @PathVariable("guid") String guid) {
    MtdColumnVO mtdColumnVO = atlasMetaDataService.getColumnDetailByGuid(guid);
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdColumnVO);
  }

  /**
   * 根据guid删除元数据,多个guid 使用英文逗号分割
   *
   * @param guids
   * @return DefaultCommonResult
   */
  @DeleteMapping("/{guids}")
  public DefaultCommonResult deleteEntitiesByGuids(@PathVariable("guids") String guids) {
    atlasMetaDataService.deleteEntitiesByGuids(guids);
    return DefaultCommonResult.success(ResultCodeEnum.OK, null);
  }
}
