package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdClassifyService;
import com.qk.dm.metadata.vo.MtdClassifyVO;
import com.qk.dm.metadata.vo.PageResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
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
   * @param: mtdClassifyAtlasVO 元数据分类VO
   * @return: DefaultCommonResult
   */
  @PostMapping("")
  public DefaultCommonResult insert(@RequestBody @Valid MtdClassifyVO mtdClassifyVO) {
    mtdClassifyService.insert(mtdClassifyVO);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 修改元数据标签
   *
   * @param: mtdClassifyAtlasVO 元数据分类VO
   * @return: DefaultCommonResult
   */
  @PutMapping("/{id}")
  public DefaultCommonResult update(@PathVariable("id") Long id, @RequestBody @Valid MtdClassifyVO mtdClassifyVO) {
    mtdClassifyService.update(id, mtdClassifyVO);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 删除元数据标签
   *
   * @param: mtdClassifyAtlasVO 元数据分类VO
   * @return: DefaultCommonResult
   */
  @DeleteMapping("/{ids}")
  public DefaultCommonResult delete(@PathVariable("ids") String ids) {
    mtdClassifyService.delete(ids);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 查询元数据标签
   *
   * @param: pagination分页查询参数对象: page,size,sortStr
   * @return: 返回分类列表信息
   */
  @GetMapping("/page")
  public DefaultCommonResult<PageResultVO<MtdClassifyVO>> listByPage(MtdClassifyVO mtdClassifyVO) {
    return new DefaultCommonResult(ResultCodeEnum.OK,mtdClassifyService.listByPage(mtdClassifyVO));
  }

  /**
   * 查询元数据标签
   *
   * @param: pagination分页查询参数对象: page,size,sortStr
   * @return: 返回分类列表信息
   */
  @GetMapping("")
  public DefaultCommonResult<List<MtdClassifyVO>> listByAll(MtdClassifyVO mtdClassifyVO) {
    return new DefaultCommonResult(ResultCodeEnum.OK,mtdClassifyService.listByAll(mtdClassifyVO));
  }
}
