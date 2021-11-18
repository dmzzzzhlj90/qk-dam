package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.service.DqcRuleTemplateService;
import com.qk.dm.dataquality.vo.DqcRuleTemplateInfoVo;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据质量_规则模版
 *
 * @author shenpj
 * @date 2021/11/8 7:45 下午
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/rule/template")
public class DqcRuleTemplateController {

  private final DqcRuleTemplateService dqcRuleTemplateService;

  @Autowired
  public DqcRuleTemplateController(DqcRuleTemplateService dqcRuleTemplateService) {
    this.dqcRuleTemplateService = dqcRuleTemplateService;
  }

  /**
   * 新增规则模版
   *
   * @param dqcRuleTemplateVo
   * @return
   */
  @PostMapping("")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
  public DefaultCommonResult insert(@RequestBody @Validated DqcRuleTemplateVo dqcRuleTemplateVo) {
    dqcRuleTemplateService.insert(dqcRuleTemplateVo);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑规则模版
   *
   * @param dqcRuleTemplateVo
   * @return
   */
  @PutMapping("")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
  public DefaultCommonResult update(@RequestBody @Validated DqcRuleTemplateVo dqcRuleTemplateVo) {
    dqcRuleTemplateService.update(dqcRuleTemplateVo);
    return DefaultCommonResult.success();
  }

  /**
   * 发布
   *
   * @param dqcRuleTemplateVo
   * @return
   */
  @PutMapping("release")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
  public DefaultCommonResult release(@RequestBody DqcRuleTemplateVo dqcRuleTemplateVo) {
    dqcRuleTemplateService.release(dqcRuleTemplateVo);
    return DefaultCommonResult.success();
  }

  /**
   * 删除单个规则模版
   *
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
  public DefaultCommonResult deleteOne(@PathVariable("id") Long id) {
    dqcRuleTemplateService.deleteOne(id);
    return DefaultCommonResult.success();
  }

  /**
   * 批量删除规则模版
   *
   * @param ids
   * @return
   */
  @DeleteMapping("/root/{ids}")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
  public DefaultCommonResult deleteBulk(@PathVariable("ids") String ids) {
    dqcRuleTemplateService.deleteBulk(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 规则模版详情
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public DefaultCommonResult<DqcRuleTemplateInfoVo> detail(@PathVariable("id") Long id) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dqcRuleTemplateService.detail(id));
  }

  /**
   * 规则模版下拉列表
   *
   * @param dqcRuleTemplateVo
   * @return
   */
  @GetMapping("/list")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
  public DefaultCommonResult<List<DqcRuleTemplateInfoVo>> search(
      DqcRuleTemplateVo dqcRuleTemplateVo) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dqcRuleTemplateService.search(dqcRuleTemplateVo));
  }

  /**
   * 规则模版分页列表
   *
   * @param dqcRuleTemplateVo
   * @param pagination
   * @return
   */
  @GetMapping("/page/list")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
  public DefaultCommonResult<PageResultVO<DqcRuleTemplateInfoVo>> searchPageList(
      DqcRuleTemplateVo dqcRuleTemplateVo, Pagination pagination) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dqcRuleTemplateService.searchPageList(dqcRuleTemplateVo, pagination));
  }
}
