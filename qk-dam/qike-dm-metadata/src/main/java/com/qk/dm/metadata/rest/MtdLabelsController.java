package com.qk.dm.metadata.rest;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdLabelsService;
import com.qk.dm.metadata.vo.MtdLabelsInfoVO;
import com.qk.dm.metadata.vo.MtdLabelsListVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import com.qk.dm.metadata.vo.PageResultVO;
import java.util.List;
import org.springframework.validation.annotation.Validated;
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
//  @Auth(bizType = BizResource.MTD_LABELS, actionType = RestActionType.CREATE)
  public DefaultCommonResult insert(@RequestBody @Validated MtdLabelsVO mtdLabelsVO) {
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
//  @Auth(bizType = BizResource.MTD_LABELS, actionType = RestActionType.UPDATE)
  public DefaultCommonResult update(
      @PathVariable("id") Long id, @RequestBody @Validated MtdLabelsVO mtdLabelsVO) {
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
//  @Auth(bizType = BizResource.MTD_LABELS, actionType = RestActionType.DELETE)
  public DefaultCommonResult delete(@PathVariable("ids") String ids) {
    mtdLabelsService.delete(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 分页查询元数据标签
   *
   * @param mtdLabelsListVO 分页元数据标签VO
   * @return DefaultCommonResult<PageResultVO<MtdLabelsInfoVO>>
   */
  @GetMapping("/page")
//  @Auth(bizType = BizResource.MTD_LABELS, actionType = RestActionType.LIST)
  public DefaultCommonResult<PageResultVO<MtdLabelsInfoVO>> listByPage(
      MtdLabelsListVO mtdLabelsListVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, mtdLabelsService.listByPage(mtdLabelsListVO));
  }

  /**
   * 查询元数据标签
   *
   * @param mtdLabelsVO 元数据标签VO
   * @return DefaultCommonResult<List<MtdLabelsInfoVO>>
   */
  @GetMapping("")
//  @Auth(bizType = BizResource.MTD_LABELS, actionType = RestActionType.LIST)
  public DefaultCommonResult<List<MtdLabelsInfoVO>> listByAll(MtdLabelsVO mtdLabelsVO) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdLabelsService.listByAll(mtdLabelsVO));
  }
}
