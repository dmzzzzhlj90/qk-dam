package com.qk.dm.metadata.rest;

import com.qk.dam.authorization.Auth;
import com.qk.dam.authorization.BizResource;
import com.qk.dam.authorization.RestActionType;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdClassifyAtlasService;
import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 元数据分类绑定相关接口
 *
 * @author wangzp
 * @date 2021/7/31 12:21
 * @since 1.0.0
 */
@RestController
@RequestMapping("/classify/bind")
public class MtdClassifyAtlasController {

  private final MtdClassifyAtlasService mtdClassifyAtlasService;

  @Autowired
  public MtdClassifyAtlasController(MtdClassifyAtlasService mtdClassifyAtlasService) {
    this.mtdClassifyAtlasService = mtdClassifyAtlasService;
  }
  /**
   * 新增元数据分类绑定关系
   *
   * @param mtdClassifyAtlasVO 元数据分类VO
   * @return DefaultCommonResult
   */
  @PostMapping("")
  @Auth(bizType = BizResource.MTD_CLASS_BIND, actionType = RestActionType.CREATE)
  public DefaultCommonResult insert(@RequestBody @Validated MtdClassifyAtlasVO mtdClassifyAtlasVO) {
    mtdClassifyAtlasService.insert(mtdClassifyAtlasVO);
    return DefaultCommonResult.success();
  }

  /**
   * 修改元数据分类绑定关系
   *
   * @param mtdClassifyAtlasVO 元数据分类VO
   * @return DefaultCommonResult
   */
  @PutMapping("")
  @Auth(bizType = BizResource.MTD_CLASS_BIND, actionType = RestActionType.UPDATE)
  public DefaultCommonResult update(@RequestBody @Validated MtdClassifyAtlasVO mtdClassifyAtlasVO) {
    mtdClassifyAtlasService.update(mtdClassifyAtlasVO);
    return DefaultCommonResult.success();
  }

  /**
   * 查询元数据分类绑定关系
   *
   * @param guid 元数据guid
   * @return DefaultCommonResult<MtdClassifyAtlasVO>
   */
  @GetMapping("/{guid}")
  @Auth(bizType = BizResource.MTD_CLASS_BIND, actionType = RestActionType.DETAIL)
  public DefaultCommonResult<MtdClassifyAtlasVO> getByGuid(@PathVariable("guid") String guid) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdClassifyAtlasService.getByGuid(guid));
  }
}
