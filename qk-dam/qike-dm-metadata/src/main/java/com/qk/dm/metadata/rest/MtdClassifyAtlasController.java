package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdClassifyAtlasService;
import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author wangzp
 * @date 2021/7/31 12:21
 * @since 1.0.0
 */
@RestController
@RequestMapping("/classifyAtlas")
public class MtdClassifyAtlasController {

  private final MtdClassifyAtlasService mtdClassifyAtlasService;

  @Autowired
  public MtdClassifyAtlasController(MtdClassifyAtlasService mtdClassifyAtlasService) {
    this.mtdClassifyAtlasService = mtdClassifyAtlasService;
  }
  /**
   * 新增元数据分类绑定关系
   *
   * @param: mtdClassifyAtlasVO 元数据分类VO
   * @return: DefaultCommonResult
   */
  @PostMapping("")
  public DefaultCommonResult insert(@RequestBody @Valid MtdClassifyAtlasVO mtdClassifyAtlasVO) {
    mtdClassifyAtlasService.insert(mtdClassifyAtlasVO);
    return DefaultCommonResult.success();
  }

  /**
   * 修改元数据分类绑定关系
   *
   * @param: mtdClassifyAtlasVO 元数据分类VO
   * @return: DefaultCommonResult
   */
  @PutMapping("/{id}")
  public DefaultCommonResult update(
      @PathVariable("id") Long id, @RequestBody @Valid MtdClassifyAtlasVO mtdClassifyAtlasVO) {
    mtdClassifyAtlasVO.setId(id);
    mtdClassifyAtlasService.update(mtdClassifyAtlasVO);
    return DefaultCommonResult.success();
  }

  /**
   * 查询元数据分类绑定关系
   *
   * @param: guid 元数据guid
   * @return: 返回标签列表信息
   */
  @GetMapping("/{guid}")
  public DefaultCommonResult<MtdClassifyAtlasVO> getByGuid(@PathVariable("guid") String guid) {
    return DefaultCommonResult.success(ResultCodeEnum.OK,mtdClassifyAtlasService.getByGuid(guid));
  }
}
