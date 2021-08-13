package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdLabelsService;
import com.qk.dm.metadata.service.impl.SynchAtalsServiceImpl;
import com.qk.dm.metadata.vo.MtdLabelsInfoVO;
import com.qk.dm.metadata.vo.MtdLabelsListVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import com.qk.dm.metadata.vo.PageResultVO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 元数据标签功能
 *
 * @author spj
 * @date 2021/8/5 4:36 下午
 * @since 1.0.0
 */
@RestController
@RequestMapping("/labels")
public class MtdLabelsController {

  private final MtdLabelsService mtdLabelsService;

  public MtdLabelsController(MtdLabelsService mtdLabelsService) {
    this.mtdLabelsService = mtdLabelsService;
  }

  /**
   * 新增元数据标签
   *
   * @param mtdLabelsVO 元数据标签VO
   * @return DefaultCommonResult
   */
  @PostMapping("")
  public DefaultCommonResult insert(@RequestBody @Valid MtdLabelsVO mtdLabelsVO) {
    mtdLabelsService.insert(mtdLabelsVO);
    return DefaultCommonResult.success();
  }

  /**
   * 修改元数据标签
   *
   * @param mtdLabelsVO 元数据标签VO
   * @return DefaultCommonResult
   */
  @PutMapping("/{id}")
  public DefaultCommonResult update(
      @PathVariable("id") Long id, @RequestBody @Valid MtdLabelsVO mtdLabelsVO) {
    mtdLabelsService.update(id, mtdLabelsVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除元数据标签
   *
   * @param ids id字符串
   * @return DefaultCommonResult
   */
  @DeleteMapping("/{ids}")
  public DefaultCommonResult delete(@PathVariable("ids") String ids) {
    mtdLabelsService.delete(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 分页查询元数据标签
   *
   * @param mtdLabelsListVO 入参
   * @return 返回标签列表信息
   */
  @GetMapping("/page")
  public DefaultCommonResult<PageResultVO<MtdLabelsInfoVO>> listByPage(
      MtdLabelsListVO mtdLabelsListVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, mtdLabelsService.listByPage(mtdLabelsListVO));
  }

  /**
   * 查询元数据标签
   *
   * @param mtdLabelsVO 元数据标签VO
   * @return 返回标签列表信息
   */
  @GetMapping("")
  public DefaultCommonResult<List<MtdLabelsInfoVO>> listByAll(MtdLabelsVO mtdLabelsVO) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdLabelsService.listByAll(mtdLabelsVO));
  }

  @Autowired SynchAtalsServiceImpl synchAtalsService;

  /**
   * 刷新标签绑定
   * @return success
   */
  @GetMapping("/synchLabelsAtlas")
  public DefaultCommonResult synchLabelsAtlas() {
    synchAtalsService.synchLabelsAtlas();
    return DefaultCommonResult.success();
  }

  /**
   * 刷新分类
   * @return success
   */
  @GetMapping("/synchClassify")
  public DefaultCommonResult synchClassify() {
    synchAtalsService.synchClassify();
    return DefaultCommonResult.success();
  }

  /**
   * 刷新分类绑定
   * @return success
   */
  @GetMapping("/synchClassifyAtlas")
  public DefaultCommonResult synchClassifyAtlas() {
    synchAtalsService.synchClassifyAtlas();
    return DefaultCommonResult.success();
  }
}
