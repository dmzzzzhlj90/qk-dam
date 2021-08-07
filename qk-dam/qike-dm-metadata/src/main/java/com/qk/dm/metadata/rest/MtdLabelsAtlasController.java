package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdLabelsAtlasService;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/labels/atlas")
public class MtdLabelsAtlasController {

  private final MtdLabelsAtlasService mtdLabelsAtlasService;

  public MtdLabelsAtlasController(MtdLabelsAtlasService mtdLabelsAtlasService) {
    this.mtdLabelsAtlasService = mtdLabelsAtlasService;
  }

  /**
   * 新增元数据标签绑定关系
   *
   * @param: mtdLabelsVO 元数据标签VO
   * @return: DefaultCommonResult
   */
  @PostMapping("")
  public DefaultCommonResult insert(@RequestBody @Valid MtdLabelsAtlasVO mtdLabelsVO) {
    mtdLabelsAtlasService.insert(mtdLabelsVO);
    return DefaultCommonResult.success();
  }

  /**
   * 修改元数据标签绑定关系
   *
   * @param: mtdLabelsVO 元数据标签VO
   * @return: DefaultCommonResult
   */
  @PutMapping("/{id}")
  public DefaultCommonResult update(
      @PathVariable("id") Long id, @RequestBody @Valid MtdLabelsAtlasVO mtdLabelsVO) {
    mtdLabelsAtlasService.update(id, mtdLabelsVO);
    return DefaultCommonResult.success();
  }

  /**
   * 查询元数据标签绑定关系
   *
   * @param: mtdLabelsVO 元数据标签VO
   * @return: 返回标签列表信息
   */
  @GetMapping("/{guid}")
  public DefaultCommonResult<MtdLabelsAtlasVO> getByGuid(@PathVariable("guid") String guid) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdLabelsAtlasService.getByGuid(guid));
  }
}
