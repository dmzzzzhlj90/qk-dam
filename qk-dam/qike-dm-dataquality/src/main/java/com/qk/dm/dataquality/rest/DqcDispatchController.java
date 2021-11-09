package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.service.DqcDispatchService;
import com.qk.dm.dataquality.vo.DqcDispatchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author shenpj
 * @date 2021/11/9 10:41 上午
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/dispatch/date")
public class DqcDispatchController {
  private final DqcDispatchService dqcDispatchService;

  public DqcDispatchController(DqcDispatchService dqcDispatchService) {
    this.dqcDispatchService = dqcDispatchService;
  }

  @PostMapping("")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
  public DefaultCommonResult insert(@RequestBody @Validated DqcDispatchVo dqcDispatchVo) {
    dqcDispatchService.insert(dqcDispatchVo);
    return DefaultCommonResult.success();
  }

  @PutMapping("")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
  public DefaultCommonResult update(@RequestBody @Validated DqcDispatchVo dqcDispatchVo) {
    dqcDispatchService.update(dqcDispatchVo);
    return DefaultCommonResult.success();
  }

  @DeleteMapping("/{id}")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
  public DefaultCommonResult delete(@PathVariable("id") Integer id) {
    dqcDispatchService.delete(id);
    return DefaultCommonResult.success();
  }
}
