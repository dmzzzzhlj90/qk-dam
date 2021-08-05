package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdLabelsService;
import com.qk.dm.metadata.vo.MtdLabelsInfoVO;
import com.qk.dm.metadata.vo.MtdLabelsListVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import com.qk.dm.metadata.vo.PageResultVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
   * @param: mtdLabelsVO 元数据标签VO
   * @return: DefaultCommonResult
   */
  @PostMapping("")
  public DefaultCommonResult insert(@RequestBody @Valid MtdLabelsVO mtdLabelsVO) {
    mtdLabelsService.insert(mtdLabelsVO);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 修改元数据标签
   *
   * @param: mtdLabelsVO 元数据标签VO
   * @return: DefaultCommonResult
   */
  @PutMapping("/{id}")
  public DefaultCommonResult update(@PathVariable("id") Long id, @RequestBody @Valid MtdLabelsVO mtdLabelsVO) {
    mtdLabelsService.update(id, mtdLabelsVO);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 删除元数据标签
   *
   * @param: ids id字符串
   * @return: DefaultCommonResult
   */
  @DeleteMapping("/{ids}")
  public DefaultCommonResult delete(@PathVariable("ids") String ids) {
    mtdLabelsService.delete(ids);
    return new DefaultCommonResult(ResultCodeEnum.OK);
  }

  /**
   * 分页查询元数据标签
   *
   * @param: pagination分页查询参数对象: page,size,sortStr
   * @return: 返回标签列表信息
   */
  @GetMapping("/page")
  public DefaultCommonResult<PageResultVO<MtdLabelsInfoVO>> listByPage(MtdLabelsListVO mtdLabelsListVO) {
    return new DefaultCommonResult(ResultCodeEnum.OK,mtdLabelsService.listByPage(mtdLabelsListVO));
  }

  /**
   * 查询元数据标签
   *
   * @param: mtdLabelsVO 元数据标签VO
   * @return: 返回标签列表信息
   */
  @GetMapping("")
  public DefaultCommonResult<List<MtdLabelsInfoVO>> listByAll(MtdLabelsVO mtdLabelsVO) {
    return new DefaultCommonResult(ResultCodeEnum.OK,mtdLabelsService.listByAll(mtdLabelsVO));
  }
}
