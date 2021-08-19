package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdClassifyService;
import com.qk.dm.metadata.vo.MtdClassifyInfoVO;
import com.qk.dm.metadata.vo.MtdClassifyListVO;
import com.qk.dm.metadata.vo.MtdClassifyVO;
import com.qk.dm.metadata.vo.PageResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 元数据分类相关基础接口
 *
 * @author wangzp
 * @date 2021/7/31 14:21
 * @since 1.0.0
 */
@RestController
@RequestMapping("/classify")
public class MtdClassifyController {
  private final MtdClassifyService mtdClassifyService;

  @Autowired
  public MtdClassifyController(MtdClassifyService mtdClassifyService) {
    this.mtdClassifyService = mtdClassifyService;
  }

  /**
   * 新增元数据分类
   *
   * @param mtdClassifyVO 元数据分类VO
   * @return DefaultCommonResult
   */
  @PostMapping("")
  public DefaultCommonResult insert(@RequestBody @Valid MtdClassifyVO mtdClassifyVO) {
    mtdClassifyService.insert(mtdClassifyVO);
    return DefaultCommonResult.success();
  }

  /**
   * 修改元数据分类
   *
   * @param mtdClassifyVO 元数据分类VO
   * @return DefaultCommonResult
   */
  @PutMapping("/{id}")
  public DefaultCommonResult update(
      @PathVariable("id") Long id, @RequestBody @Valid MtdClassifyVO mtdClassifyVO) {
    mtdClassifyService.update(id, mtdClassifyVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除元数据分类
   *
   * @param ids id集合，String类型
   * @return DefaultCommonResult
   */
  @DeleteMapping("/{ids}")
  public DefaultCommonResult delete(@PathVariable("ids") String ids) {
    mtdClassifyService.delete(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 分页查询元数据分类
   *
   * @param mtdClassifyListVO
   * @return DefaultCommonResult<PageResultVO<MtdClassifyInfoVO>>
   */
  @GetMapping("/page")
  public DefaultCommonResult<PageResultVO<MtdClassifyInfoVO>> classifyByPage(
      MtdClassifyListVO mtdClassifyListVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, mtdClassifyService.listByPage(mtdClassifyListVO));
  }

  /**
   * 查询元数据分类
   *
   * @param mtdClassifyVO
   * @return DefaultCommonResult<List<MtdClassifyInfoVO>>
   */
  @GetMapping("")
  public DefaultCommonResult<List<MtdClassifyInfoVO>> classifyByAll(MtdClassifyVO mtdClassifyVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, mtdClassifyService.listByAll(mtdClassifyVO));
  }
}
