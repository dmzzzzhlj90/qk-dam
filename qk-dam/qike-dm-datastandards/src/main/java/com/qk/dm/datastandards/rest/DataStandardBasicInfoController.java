package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.vo.CodeTableFieldsVO;
import com.qk.dm.datastandards.vo.DsdBasicInfoParamsVO;
import com.qk.dm.datastandards.vo.DsdBasicInfoVO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据标准__标准基本信息接口
 *
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/basic/info")
public class DataStandardBasicInfoController {
  private final DataStandardBasicInfoService dataStandardBasicInfoService;

  @Autowired
  public DataStandardBasicInfoController(
      DataStandardBasicInfoService dataStandardBasicInfoService) {
    this.dataStandardBasicInfoService = dataStandardBasicInfoService;
  }

  /**
   * 统一查询标准信息入口
   *
   * @param basicInfoParamsVO
   * @return DefaultCommonResult<PageResultVO<DsdBasicinfoVO>>
   */
  @PostMapping(value = "/query")
  public DefaultCommonResult<PageResultVO<DsdBasicInfoVO>> getDsdBasicInfoByDsdLevelId(
      @RequestBody DsdBasicInfoParamsVO basicInfoParamsVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dataStandardBasicInfoService.getDsdBasicInfo(basicInfoParamsVO));
  }

  /**
   * 新增标准信息
   *
   * @param dsdBasicinfoVO
   * @return DefaultCommonResult
   */
  @PostMapping("/add")
  public DefaultCommonResult addDsdBasicInfo(
      @RequestBody @Validated DsdBasicInfoVO dsdBasicinfoVO) {
    dataStandardBasicInfoService.addDsdBasicinfo(dsdBasicinfoVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑标准信息
   *
   * @param dsdBasicinfoVO
   * @return DefaultCommonResult
   */
  @PutMapping("/update")
  public DefaultCommonResult updateDsdBasicinfo(
      @RequestBody @Validated DsdBasicInfoVO dsdBasicinfoVO) {
    dataStandardBasicInfoService.updateDsdBasicinfo(dsdBasicinfoVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除标准信息
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/delete/{id}")
  public DefaultCommonResult deleteDsdBasicinfo(@PathVariable("id") Integer id) {
    dataStandardBasicInfoService.deleteDsdBasicinfo(id);
    return DefaultCommonResult.success();
  }

  /**
   * 批量删除标准信息
   *
   * @param ids
   * @return DefaultCommonResult
   */
  @DeleteMapping("/bulk/delete/{ids}")
  public DefaultCommonResult bulkDeleteDsdBasicInfo(@PathVariable("ids") String ids) {
    dataStandardBasicInfoService.bulkDeleteDsdBasicInfo(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 引用码表字段
   *
   * @param codeDirId
   * @return DefaultCommonResult
   */
  @GetMapping("/search/code/field/by/codeDirId/{codeDirId}")
  public DefaultCommonResult<List<CodeTableFieldsVO>> getCodeFieldByCodeDirId(
      @PathVariable("codeDirId") String codeDirId) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dataStandardBasicInfoService.getCodeFieldByCodeDirId(codeDirId));
  }
}
