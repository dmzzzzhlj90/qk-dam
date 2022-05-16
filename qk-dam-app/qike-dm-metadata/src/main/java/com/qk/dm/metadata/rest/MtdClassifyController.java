package com.qk.dm.metadata.rest;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdClassifyService;
import com.qk.dm.metadata.vo.MtdClassifyVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 元数据分类相关基础接口
 *
 * @author wangzp
 * @date 2021/7/31 14:21
 * @since 1.0.0
 */
@RestController
@RequestMapping("/classify")
public class MtdClassifyController {
  private final MtdClassifyService mtdClassifyService;

  public MtdClassifyController(MtdClassifyService mtdClassifyService) {
    this.mtdClassifyService = mtdClassifyService;
  }

  /**
   * 新增元数据分类
   *
   * @param mtdClassifyVO 元数据分类VO
   * @return DefaultCommonResult
   */
  @PostMapping("")
//  @Auth(bizType = BizResource.MTD_CLASS, actionType = RestActionType.CREATE)
  public DefaultCommonResult insert(@RequestBody @Validated MtdClassifyVO mtdClassifyVO) {
    mtdClassifyService.insert(mtdClassifyVO);
    return DefaultCommonResult.success();
  }

//  /**
//   * 修改元数据分类
//   *
//   * @param mtdClassifyVO 元数据分类VO
//   * @return DefaultCommonResult
//   */
//  @PutMapping("/{id}")
////  @Auth(bizType = BizResource.MTD_CLASS, actionType = RestActionType.UPDATE)
//  public DefaultCommonResult update(
//      @PathVariable("id") Long id, @RequestBody @Validated MtdClassifyVO mtdClassifyVO) {
//    mtdClassifyService.update(id, mtdClassifyVO);
//    return DefaultCommonResult.success();
//  }

  /**
   * 删除元数据分类
   * @param mtdClassifyVO
   * @return DefaultCommonResult
   */
  @DeleteMapping("")
//  @Auth(bizType = BizResource.MTD_CLASS, actionType = RestActionType.DELETE)
  public DefaultCommonResult delete(@RequestBody MtdClassifyVO mtdClassifyVO) {
    mtdClassifyService.delete(mtdClassifyVO);
    return DefaultCommonResult.success();
  }

//  /**
//   * 分页查询元数据分类
//   *
//   * @param mtdClassifyListVO
//   * @return DefaultCommonResult<PageResultVO<MtdClassifyVO>>
//   */
//  @GetMapping("/page")
////  @Auth(bizType = BizResource.MTD_CLASS, actionType = RestActionType.LIST)
//  public DefaultCommonResult<PageResultVO<MtdClassifyVO>> classifyByPage(@RequestBody MtdClassifyListVO mtdClassifyListVO) {
//    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdClassifyService.listByPage(mtdClassifyListVO));
//  }

  /**
   * 查询元数据分类
   *
   * @param mtdClassifyVO
   * @return DefaultCommonResult<List<MtdClassifyVO>>
   */
  @GetMapping("/list")
//  @Auth(bizType = BizResource.MTD_CLASS, actionType = RestActionType.DETAIL)
  public DefaultCommonResult<List<MtdClassifyVO>> classifyByAll(MtdClassifyVO mtdClassifyVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, mtdClassifyService.listByAll(mtdClassifyVO));
  }
}
