package com.qk.dm.metadata.rest;

import com.qk.dam.authorization.Auth;
import com.qk.dam.authorization.BizResource;
import com.qk.dam.authorization.RestActionType;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdLabelsAtlasService;
import com.qk.dm.metadata.vo.MtdLabelsAtlasBulkVO;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 元数据标签绑定功能
 *
 * @author spj
 * @date 2021/8/5 4:36 下午
 * @since 1.0.0
 */
@RestController
@RequestMapping("/labels/bind")
public class MtdLabelsAtlasController {

  private final MtdLabelsAtlasService mtdLabelsAtlasService;

  public MtdLabelsAtlasController(MtdLabelsAtlasService mtdLabelsAtlasService) {
    this.mtdLabelsAtlasService = mtdLabelsAtlasService;
  }

  /**
   * 新增元数据标签绑定关系
   *
   * @param mtdLabelsVO 元数据标签VO
   * @return DefaultCommonResult
   */
  @PostMapping("")
  @Auth(bizType = BizResource.MTD_LABELS_BIND, actionType = RestActionType.CREATE)
  public DefaultCommonResult insert(@RequestBody @Validated MtdLabelsAtlasVO mtdLabelsVO) {
    mtdLabelsAtlasService.insert(mtdLabelsVO);
    return DefaultCommonResult.success();
  }

  /**
   * 修改元数据标签绑定关系
   *
   * @param mtdLabelsVO 元数据标签VO
   * @return DefaultCommonResult
   */
  @PutMapping("")
  @Auth(bizType = BizResource.MTD_LABELS_BIND, actionType = RestActionType.UPDATE)
  public DefaultCommonResult update(@RequestBody @Validated MtdLabelsAtlasVO mtdLabelsVO) {
    mtdLabelsAtlasService.update(mtdLabelsVO);
    return DefaultCommonResult.success();
  }

  /**
   * 查询元数据标签绑定关系
   *
   * @param guid 元数据唯一标识
   * @return DefaultCommonResult<MtdLabelsAtlasVO>
   */
  @GetMapping("/{guid}")
  @Auth(bizType = BizResource.MTD_LABELS_BIND, actionType = RestActionType.DETAIL)
  public DefaultCommonResult<MtdLabelsAtlasVO> getByGuid(@PathVariable("guid") String guid) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdLabelsAtlasService.getByGuid(guid));
  }

  /**
   * 批量绑定元数据标签
   *
   * @param mtdLabelsVO
   * @return DefaultCommonResult
   */
  @PostMapping("/bulk")
  @Auth(bizType = BizResource.MTD_LABELS_BIND, actionType = RestActionType.BIND)
  public DefaultCommonResult bulk(@RequestBody @Validated MtdLabelsAtlasBulkVO mtdLabelsVO) {
    mtdLabelsAtlasService.bulk(mtdLabelsVO);
    return DefaultCommonResult.success();
  }
}
