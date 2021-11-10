package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.service.DqcRuleTemplateService;
import com.qk.dm.dataquality.vo.DqcRuleTemplateListVo;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVo;
import com.qk.dm.dataquality.vo.PageResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
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

  @PostMapping("")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
  public DefaultCommonResult insert(@RequestBody @Validated DqcRuleTemplateVo dqcRuleTemplateVo) {
    dqcRuleTemplateService.insert(dqcRuleTemplateVo);
    return DefaultCommonResult.success();
  }

  @PutMapping("/{id}")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
  public DefaultCommonResult update(@PathVariable("id") Long id,@RequestBody @Validated DqcRuleTemplateVo dqcRuleTemplateVo) {
    dqcRuleTemplateService.update(id,dqcRuleTemplateVo);
    return DefaultCommonResult.success();
  }

  @DeleteMapping("/{id}")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
  public DefaultCommonResult delete(@PathVariable("id") Long id) {
    dqcRuleTemplateService.delete(id);
    return DefaultCommonResult.success();
  }

  @DeleteMapping("/root/{id}")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
  public DefaultCommonResult deleteBulk(@PathVariable("id") Long id) {
    dqcRuleTemplateService.deleteBulk(id);
    return DefaultCommonResult.success();
  }

  @GetMapping("/page/list")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
  public DefaultCommonResult<PageResultVO<DqcRuleTemplateListVo>> searchPageList(
          Pagination pagination) {
    return DefaultCommonResult.success(
            ResultCodeEnum.OK, dqcRuleTemplateService.searchPageList(pagination));
  }
}
