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

//  /**
//   * 实例状态
//   * @param id
//   * @return
//   */
//  @GetMapping("/{id}/instance")
//  //    @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
//  public DefaultCommonResult<DqcProcessInstanceVO> instanceDetailByList(@PathVariable("id") Long id) {
//    return DefaultCommonResult.success(
//        ResultCodeEnum.OK, dqcSchedulerExecutorsService.instanceDetailByList(id));
//  }
//
//  @PostMapping("/schedule")
//  public DefaultCommonResult create(@RequestBody @Validated DqcSchedulerDTO dqcSchedulerDTO) {
//    dqcSchedulerExecutorsService.createSchedule(dqcSchedulerDTO);
//    return DefaultCommonResult.success();
//  }
//
//  @PutMapping("/schedule/{scheduleId}")
//  public DefaultCommonResult update(@PathVariable("scheduleId") Integer scheduleId, @RequestBody @Validated DqcSchedulerDTO dqcSchedulerDTO) {
//    dqcSchedulerExecutorsService.update(scheduleId, dqcSchedulerDTO);
//    return DefaultCommonResult.success();
//  }
//
//  @PutMapping("/schedule/{scheduleId}/online")
//  public DefaultCommonResult online(@PathVariable("scheduleId") Integer scheduleId) {
//    dqcSchedulerExecutorsService.online(scheduleId);
//    return DefaultCommonResult.success();
//  }
//
//  @PutMapping("/schedule/{scheduleId}/offline")
//  public DefaultCommonResult offline(@PathVariable("scheduleId") Integer scheduleId) {
//    dqcSchedulerExecutorsService.offline(scheduleId);
//    return DefaultCommonResult.success();
//  }
//
//  @DeleteMapping("/schedule/{scheduleId}")
//  public DefaultCommonResult deleteOne(@PathVariable("scheduleId") Integer scheduleId,@RequestBody @Validated  DqcSchedulerDTO dqcSchedulerDTO) {
//    dqcSchedulerExecutorsService.deleteOne(scheduleId,dqcSchedulerDTO);
//    return DefaultCommonResult.success();
//  }
//
//  @GetMapping("/schedule")
//  public DefaultCommonResult search(Integer processDefinitionId) {
//    return DefaultCommonResult.success(
//            ResultCodeEnum.OK, dqcSchedulerExecutorsService.search(processDefinitionId));
//  }
}