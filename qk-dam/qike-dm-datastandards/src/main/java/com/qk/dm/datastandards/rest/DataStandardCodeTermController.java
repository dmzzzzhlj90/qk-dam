package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.service.DataStandardCodeTermService;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.params.DsdCodeTermParamsVO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据标准__码表术语接口
 *
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/code/term")
public class DataStandardCodeTermController {
  private final DataStandardCodeTermService dataStandardCodeTermService;

  @Autowired
  public DataStandardCodeTermController(DataStandardCodeTermService dataStandardCodeTermService) {
    this.dataStandardCodeTermService = dataStandardCodeTermService;
  }

  /**
   * 统一查询标准信息入口
   *
   * @param: pagination分页查询参数对象: page,size,sortStr
   * @return: 返回数据标准码表术语列表信息
   */
  @PostMapping(value = "/query")
  public DefaultCommonResult<PageResultVO<DsdCodeTermVO>> getDsdCodeTerm(
      @RequestBody DsdCodeTermParamsVO dsdCodeTermParamsVO) {
    return new DefaultCommonResult(
        ResultCodeEnum.OK, dataStandardCodeTermService.getDsdCodeTerm(dsdCodeTermParamsVO));
  }

  /**
   * 新增数据标准码表术语信息
   *
   * @param: dsdCodeTermVO 数据标准码表术语VO
   * @return: DefaultCommonResult
   */
  @PostMapping("/add")
  public DefaultCommonResult addDsdCodeTerm(@RequestBody @Validated DsdCodeTermVO dsdCodeTermVO) {
    dataStandardCodeTermService.addDsdCodeTerm(dsdCodeTermVO);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 批量新增数据标准码表术语信息
   *
   * @param: dsdCodeTermVO 数据标准码表术语VO
   * @return: DefaultCommonResult
   */
  @PostMapping("/bulk/add")
  public DefaultCommonResult bulkAddDsdCodeTerm(
      @RequestBody @Validated List<DsdCodeTermVO> dsdCodeTermVOList) {
    dataStandardCodeTermService.bulkAddDsdCodeTerm(dsdCodeTermVOList);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 编辑数据标准码表术语信息
   *
   * @param: dsdCodeTermVO 数据标准码表术语VO
   * @return: DefaultCommonResult
   */
  @PutMapping("/update")
  public DefaultCommonResult updateDsdCodeTerm(
      @RequestBody @Validated DsdCodeTermVO dsdCodeTermVO) {
    dataStandardCodeTermService.updateDsdCodeTerm(dsdCodeTermVO);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 批量编辑数据标准码表术语信息 TODO 批量更新考虑效率问题
   *
   * @param: dsdCodeTermVO 数据标准码表术语VO
   * @return: DefaultCommonResult
   */
  @PutMapping("/bulk/update")
  public DefaultCommonResult bulkUpdateDsdCodeTerm(
      @RequestBody @Validated List<DsdCodeTermVO> dsdCodeTermVOList) {
    dataStandardCodeTermService.bulkUpdateDsdCodeTerm(dsdCodeTermVOList);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 删除数据标准码表术语信息
   *
   * @param: id
   * @return: DefaultCommonResult
   */
  @DeleteMapping("/delete/{id}")
  public DefaultCommonResult deleteDsdCodeTerm(@PathVariable("id") Integer id) {
    dataStandardCodeTermService.deleteDsdCodeTerm(id);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 批量删除标准信息
   *
   * @param: id
   * @return: DefaultCommonResult
   */
  @DeleteMapping("/bulk/delete/{ids}")
  public DefaultCommonResult bulkDeleteDsdTerm(@PathVariable("ids") String ids) {
    dataStandardCodeTermService.bulkDeleteDsdTerm(ids);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 根据id获取码表详情信息
   *
   * @param: id
   * @return: DefaultCommonResult
   */
  @GetMapping("/get/by/{id}")
  public DefaultCommonResult<DsdCodeTermVO> getDsdCodeTermById(@PathVariable("id") Integer id) {
    return new DefaultCommonResult(
        ResultCodeEnum.OK, dataStandardCodeTermService.getDsdCodeTermById(id));
  }
}
