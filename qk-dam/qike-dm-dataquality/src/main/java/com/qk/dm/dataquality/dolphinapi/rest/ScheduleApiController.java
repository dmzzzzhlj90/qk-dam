package com.qk.dm.dataquality.dolphinapi.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.dolphinapi.service.ScheduleApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author shenpj
 * @date 2021/11/25 2:20 下午
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/scheduler")
public class ScheduleApiController {
  private final ScheduleApiService scheduleApiService;

  public ScheduleApiController(ScheduleApiService scheduleApiService) {
    this.scheduleApiService = scheduleApiService;
  }

  @PostMapping("")
  public DefaultCommonResult save(
      Integer processDefinitionId,
      @RequestBody @Validated DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    scheduleApiService.create(processDefinitionId, dqcSchedulerConfigVO);
    return DefaultCommonResult.success();
  }

  @PutMapping("/{scheduleId}")
  public DefaultCommonResult update(
      @PathVariable("scheduleId") Integer id,
      @RequestBody @Validated DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    scheduleApiService.update(id, dqcSchedulerConfigVO);
    return DefaultCommonResult.success();
  }

  @PutMapping("/{scheduleId}/online")
  public DefaultCommonResult online(@PathVariable("scheduleId") Integer id) {
    scheduleApiService.online(id);
    return DefaultCommonResult.success();
  }

  @PutMapping("/{scheduleId}/offline")
  public DefaultCommonResult offline(@PathVariable("scheduleId") Integer id) {
    scheduleApiService.offline(id);
    return DefaultCommonResult.success();
  }

  @DeleteMapping("/{scheduleId}")
  public DefaultCommonResult deleteOne(@PathVariable("scheduleId") Integer id) {
    scheduleApiService.deleteOne(id);
    return DefaultCommonResult.success();
  }

  @GetMapping("")
  public DefaultCommonResult search(Integer processDefinitionId, Integer pageNo, Integer pageSize) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, scheduleApiService.search(processDefinitionId, pageNo, pageSize, null));
  }
}
