package com.qk.dm.metadata.rest;

import com.qk.dm.metadata.respose.ResponseWrapper;
import com.qk.dm.metadata.service.MtdLabelsService;
import com.qk.dm.metadata.vo.MtdLabelsInfoVO;
import com.qk.dm.metadata.vo.MtdLabelsListVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import com.qk.dm.metadata.vo.PageResultVO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
  @ResponseWrapper
  public void insert(@RequestBody @Valid MtdLabelsVO mtdLabelsVO) {
    mtdLabelsService.insert(mtdLabelsVO);
  }

  /**
   * 修改元数据标签
   *
   * @param: mtdLabelsVO 元数据标签VO
   * @return: DefaultCommonResult
   */
  @PutMapping("/{id}")
  @ResponseWrapper
  public void update(@PathVariable("id") Long id, @RequestBody @Valid MtdLabelsVO mtdLabelsVO) {
    mtdLabelsService.update(id, mtdLabelsVO);
  }

  /**
   * 删除元数据标签
   *
   * @param: ids id字符串
   * @return: DefaultCommonResult
   */
  @DeleteMapping("/{ids}")
  @ResponseWrapper
  public void delete(@PathVariable("ids") String ids) {
    mtdLabelsService.delete(ids);
  }

  /**
   * 分页查询元数据标签
   *
   * @param: pagination分页查询参数对象: page,size,sortStr
   * @return: 返回标签列表信息
   */
  @GetMapping("/page")
  @ResponseWrapper
  public PageResultVO<MtdLabelsInfoVO> listByPage(MtdLabelsListVO mtdLabelsListVO) {
    return mtdLabelsService.listByPage(mtdLabelsListVO);
  }

  /**
   * 查询元数据标签
   *
   * @param: mtdLabelsVO 元数据标签VO
   * @return: 返回标签列表信息
   */
  @GetMapping("")
  @ResponseWrapper
  public List<MtdLabelsInfoVO> listByAll(MtdLabelsVO mtdLabelsVO) {
    return mtdLabelsService.listByAll(mtdLabelsVO);
  }
}
