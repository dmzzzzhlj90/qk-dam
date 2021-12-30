package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.params.dto.DqcSchedulerReleaseDTO;
import com.qk.dm.dataquality.service.DqcSchedulerExecutorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 数据质量_规则调度_基础信息
 *
 * @author wjq
 * @date 2021/11/08
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/scheduler/executors")
public class DqcSchedulerExecutorsController {
  private final DqcSchedulerExecutorsService dqcSchedulerExecutorsService;

  public DqcSchedulerExecutorsController(DqcSchedulerExecutorsService dqcSchedulerExecutorsService) {
    this.dqcSchedulerExecutorsService = dqcSchedulerExecutorsService;
  }

  /**
   * 启动调度/停止调度
   *
   * @param dqcSchedulerReleaseDto
   * @return
   */
  @PutMapping("/release")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
  public DefaultCommonResult release(@RequestBody DqcSchedulerReleaseDTO dqcSchedulerReleaseDto) {
    dqcSchedulerExecutorsService.release(dqcSchedulerReleaseDto);
    return DefaultCommonResult.success();
  }

  /**
   * 运行
   * @param id
   * @return
   */
  @PutMapping("/runing/{id}")
  //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
  public DefaultCommonResult runing(@PathVariable("id") Long id) {
    dqcSchedulerExecutorsService.runing(id);
    return DefaultCommonResult.success();
  }
}
