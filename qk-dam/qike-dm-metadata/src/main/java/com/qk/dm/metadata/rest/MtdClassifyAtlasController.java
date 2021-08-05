package com.qk.dm.metadata.rest;

import com.qk.dm.metadata.respose.ResponseWrapper;
import com.qk.dm.metadata.service.MtdClassifyAtlasService;
import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangzp
 * @date 2021/7/31 12:21
 * @since 1.0.0
 */
@RestController
@RequestMapping("/classifyAtlas")
@ResponseWrapper
public class MtdClassifyAtlasController {

  private final MtdClassifyAtlasService mtdClassifyAtlasService;

  @Autowired
  public MtdClassifyAtlasController(MtdClassifyAtlasService mtdClassifyAtlasService) {
    this.mtdClassifyAtlasService = mtdClassifyAtlasService;
  }
  /**
   * 新增元数据分类
   *
   * @param: mtdClassifyAtlasVO 元数据分类VO
   * @return: DefaultCommonResult
   */
  @PostMapping("")
  public void insert(@RequestBody @Valid MtdClassifyAtlasVO mtdClassifyAtlasVO) {
    mtdClassifyAtlasService.insert(mtdClassifyAtlasVO);
  }

  /**
   * 修改元数据分类
   *
   * @param: mtdClassifyAtlasVO 元数据分类VO
   * @return: DefaultCommonResult
   */
  @PutMapping("/{id}")
  public void update(
      @PathVariable("id") Long id, @RequestBody @Valid MtdClassifyAtlasVO mtdClassifyAtlasVO) {
    mtdClassifyAtlasVO.setId(id);
    mtdClassifyAtlasService.update(mtdClassifyAtlasVO);
  }

  /**
   * 查询元数据分类
   *
   * @param: guid 元数据guid
   * @return: 返回标签列表信息
   */
  @GetMapping("/{guid}")
  @ResponseWrapper
  public MtdClassifyAtlasVO getByGuid(@PathVariable("guid") String guid) {
    return mtdClassifyAtlasService.getByGuid(guid);
  }
}
