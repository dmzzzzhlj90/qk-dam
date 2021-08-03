package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.vo.CodeTableFieldsVO;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.params.DsdBasicinfoParamsVO;
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
   * @param: dsdBasicinfoParamsVO:查询对象
   * @return: 返回标准列表信息
   */
  @PostMapping(value = "/query")
  public DefaultCommonResult<PageResultVO<DsdBasicinfoVO>> getDsdBasicInfoByDsdLevelId(@RequestBody DsdBasicinfoParamsVO basicinfoParamsVO) {
    return new DefaultCommonResult(
        ResultCodeEnum.OK, dataStandardBasicInfoService.getDsdBasicInfo(basicinfoParamsVO));
  }

  /**
   * 新增标准信息
   *
   * @param: dsdBasicinfoVO 数据标准信息VO
   * @return: DefaultCommonResult
   */
  @PostMapping("/add")
  public DefaultCommonResult addDsdBasicinfo(
      @RequestBody @Validated DsdBasicinfoVO dsdBasicinfoVO) {
    dataStandardBasicInfoService.addDsdBasicinfo(dsdBasicinfoVO);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 编辑标准信息
   *
   * @param: dsdBasicinfoVO 数据标准信息VO
   * @return: DefaultCommonResult
   */
  @PutMapping("/update")
  public DefaultCommonResult updateDsdBasicinfo(
      @RequestBody @Validated DsdBasicinfoVO dsdBasicinfoVO) {
    dataStandardBasicInfoService.updateDsdBasicinfo(dsdBasicinfoVO);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 删除标准信息
   *
   * @param: id
   * @return: DefaultCommonResult
   */
  @DeleteMapping("/delete/{id}")
  public DefaultCommonResult deleteDsdBasicinfo(@PathVariable("id") Integer id) {
    dataStandardBasicInfoService.deleteDsdBasicinfo(id);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 批量删除标准信息
   *
   * @param: id
   * @return: DefaultCommonResult
   */
  @DeleteMapping("/bulk/delete/{ids}")
  public DefaultCommonResult bulkDeleteDsdBasicInfo(@PathVariable("ids") String ids) {
    dataStandardBasicInfoService.bulkDeleteDsdBasicInfo(ids);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 引用码表字段
   *
   * @param: codeDirId
   * @return: DefaultCommonResult
   */
  @GetMapping("/search/code/field/by/codeDirId/{codeDirId}")
  public DefaultCommonResult<List<CodeTableFieldsVO>> getCodeFieldByCodeDirId(@PathVariable("codeDirId") String codeDirId) {
    return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardBasicInfoService.getCodeFieldByCodeDirId(codeDirId));
  }
}
